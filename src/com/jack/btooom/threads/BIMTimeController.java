package com.jack.btooom.threads;

import java.util.ArrayList;
import java.util.List;

import com.jack.btooom.Core;
import com.jack.btooom.API.BIMStoreAPI;
import com.jack.btooom.addons.AddonLoader;
import com.jack.btooom.beams.BIMs;
import com.jack.btooom.data.BIMStore;

public class BIMTimeController implements Runnable {

	public static boolean activ = true;
	public int delay = 2 * 500;
	private long calcDelay = delay;

	public void stopMe() {
		activ = false;
	}

	public BIMStoreAPI bimStore;

	public BIMTimeController() {
		bimStore = Core.getInstance().getBIMStore();
	}

	private static List<String> toUpdateIDs = new ArrayList<String>();

	@Override
	public void run() {
		while (activ) {
			// getTime at work start
			long startTime = System.currentTimeMillis();

			workAllToDo();

			// getTime after work done
			long now = System.currentTimeMillis();
			calcDelay = delay - (startTime - now); // calc diff --> real dealy

			try {

				Thread.sleep(calcDelay);

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void workAllToDo() {
		work(new ArrayList<String>(toUpdateIDs)); // may take some time
	}

	public static void addActivBIMID(String id) {
		synchronized (toUpdateIDs) {
			if (!toUpdateIDs.contains(id)) {
				toUpdateIDs.add(id);
			}
		}
	}

	public static void removeActivBIMID(String id) {
		synchronized (toUpdateIDs) {
			if (toUpdateIDs.contains(id)) {
				toUpdateIDs.remove(id);
			}
		}
	}

	private void work(List<String> ids) {
		for (String id : ids) {
			update(id);
		}
	}

	private void update(String id) {

		String typ = bimStore.getBIMType(id);
		AddonLoader.getBimClass(typ).updateAfterSecond(id);
	}

}
