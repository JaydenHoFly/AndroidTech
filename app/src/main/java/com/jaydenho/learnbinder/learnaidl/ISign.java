/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: C:\\Work\\project\\jaydenho\\AndroidTech\\app\\src\\main\\aidl\\com\\jaydenho\\learnbinder\\learnaidl\\ISign.aidl
 */
package com.jaydenho.learnbinder.learnaidl;
// Declare any non-default types here with import statements

import android.os.RemoteException;

import java.util.List;

/**
 * 客户端Binder代码
 */
public interface ISign extends android.os.IInterface {
    /**
     * Local-side IPC implementation stub class.
     */
    abstract class Stub extends android.os.Binder implements ISign {
        private static final String DESCRIPTOR = "com.jaydenho.learnbinder.learnaidl.ISign";

        /**
         * Construct the stub at attach it to the interface.
         */
        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        /**
         * Cast an IBinder object into an com.jaydenho.learnbinder.learnaidl.ISign interface,
         * generating a proxy if needed.
         */
        public static ISign asInterface(android.os.IBinder obj) {
            if ((obj == null)) {
                return null;
            }
            android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (((iin != null) && (iin instanceof ISign))) {
                return ((ISign) iin);
            }
            return new Stub.Proxy(obj);
        }

        @Override
        public android.os.IBinder asBinder() {
            return this;
        }

        @Override
        public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException {
           return true;//该方法不需要在客户端实现
        }

        private static class Proxy implements ISign {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder remote) {
                mRemote = remote;
            }

            @Override
            public android.os.IBinder asBinder() {
                return mRemote;
            }

            public String getInterfaceDescriptor() {
                return DESCRIPTOR;
            }

            @Override
            public String sign(String params) throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                String _result;
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeString(params);
                    mRemote.transact(Stub.TRANSACTION_sign, _data, _reply, 0);
                    _reply.readException();
                    _result = _reply.readString();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
                return _result;
            }

            @Override
            public String sign(int num, List<Param> params) throws RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(num);
                    _data.writeTypedList(params);
                    mRemote.transact(TRANSACTION_sign_2, _data, _reply, 0);
                    _reply.readException();
                    return _reply.readString();
                } catch (RuntimeException e) {
                    e.printStackTrace();
                    return e.getMessage();
                } finally {
                    _data.recycle();
                    _reply.recycle();
                }
            }
        }

        static final int TRANSACTION_sign = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
        static final int TRANSACTION_sign_2 = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
    }

    String sign(String params) throws android.os.RemoteException;

    String sign(int num, List<Param> params) throws android.os.RemoteException;
}
