package com.jack.btooom.util;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

public class VectorJump {

	public static void vJump(Entity entity, Location loc, double time) {
		entity.setFallDistance(0);
		entity.setVelocity(getV(entity.getLocation(), loc, time));
	}

	private static Vector getV(Location p1, Location p2, double t) {
		double x = p2.getX() - p1.getX();
		double y = p2.getY() - p1.getY();
		double z = p2.getZ() - p1.getZ();
		double gravity = 0.1;
		return new Vector(getVelocity(x, 0, t), getVelocity(y, gravity, t)+10, getVelocity(z, 0, t));
	}

	private static double getVelocity(double d, double a, double t) {
		a *= -.5;
		a *= Math.pow(t, 2);
		d -= a;
		return 2 * (d / t);
	}
}
