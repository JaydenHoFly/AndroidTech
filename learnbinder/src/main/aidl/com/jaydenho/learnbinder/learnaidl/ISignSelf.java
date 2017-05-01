/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: E:\\life\\project\\androidTech\\learnbinder\\src\\main\\aidl\\com\\jaydenho\\learnbinder\\learnaidl\\ISign.aidl
 */
package com.jaydenho.learnbinder.learnaidl;
// Declare any non-default types here with import statements

public interface ISignSelf extends android.os.IInterface {
    /**
     * Local-side IPC implementation stub class.
     */
    public static abstract class Stub extends android.os.Binder implements ISignSelf {
        private static final String DESCRIPTOR = "com.jaydenho.learnbinder.learnaidl.ISignSelf";

        /**
         * Construct the stub at attach it to the interface.
         */
        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        /**
         * Cast an IBinder object into an com.jaydenho.learnbinder.learnaidl.ISignSelf interface,
         * generating a proxy if needed.
         */
        public static ISignSelf asInterface(android.os.IBinder obj) {
            if ((obj == null)) {
                return null;
            }
            android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (((iin != null) && (iin instanceof ISignSelf))) {
                return ((ISignSelf) iin);
            }
            return new Proxy(obj);
        }

        @Override
        public android.os.IBinder asBinder() {
            return this;
        }

        @Override
        public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException {
            switch (code) {
                case INTERFACE_TRANSACTION: {
                    reply.writeString(DESCRIPTOR);
                    return true;
                }
                case TRANSACTION_sign: {
                    data.enforceInterface(DESCRIPTOR);
                    String _arg0;
                    _arg0 = data.readString();
                    String _result = this.sign(_arg0);
                    reply.writeNoException();
                    reply.writeString(_result);
                    return true;
                }
            }
            return super.onTransact(code, data, reply, flags);
        }

        private static class Proxy implements ISignSelf {
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
        }

        static final int TRANSACTION_sign = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
    }

    public String sign(String params) throws android.os.RemoteException;

}
