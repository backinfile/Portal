package com.backinfile.support;

public interface IAction extends IAlive, IDisposable {

	public default void init() {
	}

	public default boolean isDone() {
		return true;
	}

	@Override
	public default void pulse() {
	}

	@Override
	public default void dispose() {
	}
}
