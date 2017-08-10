
package yzs.itaxi.view.icall;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.telephony.TelephonyManager;

import yzs.commonlibrary.util.StringUtils;


public class PhoneStateReceiver extends BroadcastReceiver {

    /**
     * 电话管理
     */
    private TelephonyManager telMgr = null;

    private static final Object monitor = new Object();

    @Override
    public void onReceive(Context context, Intent intent) {
        final Context ctx = context;


        telMgr = (TelephonyManager) ctx.getSystemService(Service.TELEPHONY_SERVICE);
        switch (telMgr.getCallState()) {
            case TelephonyManager.CALL_STATE_RINGING:// 来电响铃
//                System.out.println("....................主人，那家伙又来电话了....................");
//                final String number = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
//                System.out.println("number:" + number);
//
//                if (number.length() == 11) {
//                    synchronized (monitor) {
//
//                        new Handler().postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                // TODO Auto-generated method stub
//                                showActivity(ctx, number);
//                            }
//                        }, 1000);
//
//                    }
//                }
                break;
            case TelephonyManager.CALL_STATE_OFFHOOK:// 接听电话
                final String number = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
                if (!StringUtils.isEmpty(number)) {
                    synchronized (monitor) {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                // TODO Auto-generated method stub
                                showActivity(ctx, number);
                            }
                        }, 1000);
                    }
                }
                break;
            case TelephonyManager.CALL_STATE_IDLE:// 挂断电话
                synchronized (monitor) {
//                    Utils.sendEndCallBroadCast(ctx);


                }
            default:
                break;

        }

    }


    /**
     * 显示来电Activity
     *
     * @param ctx
     * @param number
     */
    private void showActivity(Context ctx, String number) {
        Intent intent = new Intent(ctx, ICallActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(ICallActivity.Companion.getEXTRA_PHONE_NUM(), number);
        ctx.startActivity(intent);
    }


}
