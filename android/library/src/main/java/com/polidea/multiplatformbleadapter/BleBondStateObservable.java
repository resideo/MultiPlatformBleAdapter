package com.polidea.multiplatformbleadapter;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.NonNull;

import com.polidea.multiplatformbleadapter.errors.BleError;
import com.polidea.multiplatformbleadapter.errors.BleErrorCode;

import bleshadow.javax.inject.Inject;

import rx.Emitter;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Cancellable;
import rx.internal.operators.OnSubscribeCreate;

public class BleBondStateObservable extends Observable<BleBondStateObservable.BleBondStateChange> {
    public static class BleBondStateChange {
        private final int oldState;
        private final int newState;

        public BleBondStateChange(int oldState, int newState) {
            this.oldState = oldState;
            this.newState = newState;
        }

        private String getStateName(int state) {
            if (state == BluetoothDevice.BOND_BONDING) {
                return "BOND_BONDING";
            } else if (state == BluetoothDevice.BOND_BONDED) {
                return "BOND_BONDED";
            } else {
                return "BOND_NONE";
            }
        }

        public int getOldState() {
            return oldState;
        }

        public int getNewState() {
            return newState;
        }

        @Override
        @NonNull
        public String toString() {
            return "BleBondStateChange: " + getStateName(oldState) + " -> " + getStateName(newState);
        }
    }

    @Inject
    public BleBondStateObservable(@NonNull final Context context, @NonNull final BluetoothDevice bleDevice) {
        super(new OnSubscribeCreate<>(
                new Action1<Emitter<BleBondStateChange>>() {
                    @Override
                    public void call(final Emitter<BleBondStateChange> emitter) {
                        final BroadcastReceiver receiver = new BroadcastReceiver() {
                            @Override
                            public void onReceive(Context context, Intent intent) {
                                String action = intent.getAction();
                                if (action.equals(BluetoothDevice.ACTION_BOND_STATE_CHANGED)) {
                                    int state = intent.getIntExtra(BluetoothDevice.EXTRA_BOND_STATE, -1);
                                    int previousState = intent.getIntExtra(BluetoothDevice.EXTRA_PREVIOUS_BOND_STATE, -1);
                                    BleBondStateChange change = mapToBleBondStateChange(previousState, state);
                                    emitter.onNext(mapToBleBondStateChange(previousState, state));
                                }
                            }
                        };
                        context.registerReceiver(receiver, createFilter());
                        emitter.setCancellation(new Cancellable() {
                            @Override
                            public void cancel() throws Exception {
                                context.unregisterReceiver(receiver);
                            }
                        });
                    }
                },
                Emitter.BackpressureMode.BUFFER
        ));
    }

    private static BleBondStateChange mapToBleBondStateChange(int oldState, int newState) {
        return new BleBondStateChange(oldState, newState);
    }
    
    private static IntentFilter createFilter() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        intentFilter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        return intentFilter;
    }
}