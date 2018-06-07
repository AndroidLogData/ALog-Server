package com.logdata.common;

import com.logdata.common.util.Utility;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class UtilityTest {
    @Autowired
    private Utility utility;
    private String logcat = "01-22 20:19:51.195 D/[MainActivity::onCreate](21800): debug\n01-22 20:19:51.195 I/System.out(21800): (HTTPLog)-Static: isSBSettingEnabled false\n01-22 20:19:51.195 W/[MainActivity::onCreate](21800): warning\n01-22 20:19:51.195 I/System.out(21800): (HTTPLog)-Static: isShipBuild true\n01-22 20:19:51.195 I/System.out(21800): (HTTPLog)-Thread-140221-34964680: SmartBonding Enabling is false, SHIP_BUILD is true, log to file is false, DBG is false\n01-22 20:19:51.195 I/System.out(21800): (HTTPLog)-Static: isSBSettingEnabled false\n01-22 20:19:51.195 V/[MainActivity::onCreate](21800): verb\n01-22 20:19:51.195 D/Activity(21800): performCreate Call secproduct feature valuefalse\n01-22 20:19:51.195 D/Activity(21800): performCreate Call debug elastic valuetrue\n01-22 20:19:51.195 I/System.out(21800): KnoxVpnUidStorageknoxVpnSupported API value returned is false\n01-22 20:19:51.195 I/qtaguid (21800): Tagging socket 55 with tag 355f55a00000000{55965018,0} uid -1, pid: 21800, getuid(): 10522\n01-22 20:19:51.215 D/OpenGLRenderer(21800): Render dirty regions requested: true\n01-22 20:19:51.285 I/Adreno-EGL(21800): <qeglDrvAPI_eglInitialize:410>: EGL 1.4 QUALCOMM build: AU_LINUX_ANDROID_LA.BF.1.1_RB1.05.00.00.002.025_msm8974_LA.BF.1.1_RB1__release_AU ()\n01-22 20:19:51.285 I/Adreno-EGL(21800): OpenGL ES Shader Compiler Version: E031.25.01.03\n01-22 20:19:51.285 I/Adreno-EGL(21800): Build Date: 11/19/14 Wed\n01-22 20:19:51.285 I/Adreno-EGL(21800): Local Branch: mybranch5813579\n01-22 20:19:51.285 I/Adreno-EGL(21800): Remote Branch: quic/LA.BF.1.1_rb1.11\n01-22 20:19:51.285 I/Adreno-EGL(21800): Local Patches: NONE\n01-22 20:19:51.285 I/Adreno-EGL(21800): Reconstruct Branch: AU_LINUX_ANDROID_LA.BF.1.1_RB1.05.00.00.002.025 + 30e7589 +  NOTHING\n01-22 20:19:51.285 I/OpenGLRenderer(21800): Initialized EGL, version 1.4\n01-22 20:19:51.305 I/OpenGLRenderer(21800): HWUI protection enabled for context ,  &this =0xae994218 ,&mEglDisplay = 1 , &mEglConfig = 8 \n01-22 20:19:51.305 D/OpenGLRenderer(21800): Enabling debug mode 0\n01-22 20:19:51.345 I/qtaguid (21800): Untagging socket 55\n01-22 20:19:51.375 W/art     (21800): Before Android 4.1, method int android.support.v7.widget.ListViewCompat.lookForSelectablePosition(int, boolean) would have incorrectly overridden the package-private method in android.widget.ListView\n01-22 20:19:51.425 I/Response(21800): success\n01-22 20:19:51.435 I/System.out(21800): (HTTPLog)-Static: isSBSettingEnabled false\n01-22 20:19:51.435 I/qtaguid (21800): Tagging socket 55 with tag 355f55a00000000{55965018,0} uid -1, pid: 21800, getuid(): 10522\n01-22 20:19:51.445 I/System.out(21800): (HTTPLog)-Static: isSBSettingEnabled false\n01-22 20:19:51.445 I/System.out(21800): KnoxVpnUidStorageknoxVpnSupported API value returned is false\n01-22 20:19:51.445 I/Timeline(21800): Timeline: Activity_idle id: android.os.BinderProxy@188dd2e3 time:1082145136\n01-22 20:19:51.445 I/System.out(21800): (HTTPLog)-Static: isSBSettingEnabled false\n01-22 20:19:51.445 I/qtaguid (21800): Tagging socket 70 with tag 355f55a00000000{55965018,0} uid -1, pid: 21800, getuid(): 10522\n01-22 20:19:51.445 I/System.out(21800): KnoxVpnUidStorageknoxVpnSupported API value returned is false\n01-22 20:19:51.445 I/qtaguid (21800): Tagging socket 71 with tag 355f55a00000000{55965018,0} uid -1, pid: 21800, getuid(): 10522\n01-22 20:19:51.455 I/System.out(21800): (HTTPLog)-Static: isSBSettingEnabled false\n01-22 20:19:51.455 I/System.out(21800): KnoxVpnUidStorageknoxVpnSupported API value returned is false\n01-22 20:19:51.455 I/qtaguid (21800): Tagging socket 75 with tag 355f55a00000000{55965018,0} uid -1, pid: 21800, getuid(): 10522\n01-22 20:19:51.455 I/qtaguid (21800): Untagging socket 55\n01-22 20:19:51.465 I/qtaguid (21800): Untagging socket 70\n01-22 20:19:51.465 I/qtaguid (21800): Untagging socket 71\n01-22 20:19:51.465 I/qtaguid (21800): Untagging socket 75\n01-22 20:19:51.475 I/Response(21800): success\n01-22 20:19:51.475 I/System.out(21800): (HTTPLog)-Static: isSBSettingEnabled false\n01-22 20:19:51.475 I/qtaguid (21800): Tagging socket 55 with tag 355f55a00000000{55965018,0} uid -1, pid: 21800, getuid(): 10522\n01-22 20:19:51.475 I/Response(21800): success\n01-22 20:19:51.475 I/Response(21800): success\n01-22 20:19:51.475 I/Response(21800): success\n01-22 20:19:51.475 I/System.out(21800): (HTTPLog)-Static: isSBSettingEnabled false\n01-22 20:19:51.475 I/qtaguid (21800): Tagging socket 70 with tag 355f55a00000000{55965018,0} uid -1, pid: 21800, getuid(): 10522\n01-22 20:19:51.475 I/System.out(21800): (HTTPLog)-Static: isSBSettingEnabled false\n01-22 20:19:51.475 I/System.out(21800): (HTTPLog)-Static: isSBSettingEnabled false\n01-22 20:19:51.475 I/qtaguid (21800): Tagging socket 71 with tag 355f55a00000000{55965018,0} uid -1, pid: 21800, getuid(): 10522\n01-22 20:19:51.475 I/qtaguid (21800): Tagging socket 75 with tag 355f55a00000000{55965018,0} uid -1, pid: 21800, getuid(): 10522\n01-22 20:19:51.485 I/qtaguid (21800): Untagging socket 55\n01-22 20:19:51.495 I/Response(21800): success\n01-22 20:19:51.495 I/System.out(21800): (HTTPLog)-Static: isSBSettingEnabled false\n01-22 20:19:51.495 I/qtaguid (21800): Tagging socket 55 with tag 355f55a00000000{55965018,0} uid -1, pid: 21800, getuid(): 10522\n01-22 20:19:51.495 I/qtaguid (21800): Untagging socket 75\n01-22 20:19:51.495 I/Response(21800): success\n01-22 20:19:51.495 I/System.out(21800): (HTTPLog)-Static: isSBSettingEnabled false\n01-22 20:19:51.495 I/qtaguid (21800): Untagging socket 71\n01-22 20:19:51.495 I/qtaguid (21800): Tagging socket 75 with tag 355f55a00000000{55965018,0} uid -1, pid: 21800, getuid(): 10522\n01-22 20:19:51.495 I/qtaguid (21800): Untagging socket 70\n01-22 20:19:51.495 I/Response(21800): success\n01-22 20:19:51.495 I/System.out(21800): (HTTPLog)-Static: isSBSettingEnabled false\n01-22 20:19:51.495 I/qtaguid (21800): Tagging socket 71 with tag 355f55a00000000{55965018,0} uid -1, pid: 21800, getuid(): 10522\n01-22 20:19:51.505 I/Response(21800): success\n01-22 20:19:51.505 I/System.out(21800): (HTTPLog)-Static: isSBSettingEnabled false\n01-22 20:19:51.505 I/qtaguid (21800): Tagging socket 70 with tag 355f55a00000000{55965018,0} uid -1, pid: 21800, getuid(): 10522\n01-22 20:19:51.505 I/qtaguid (21800): Untagging socket 75\n01-22 20:19:51.505 I/qtaguid (21800): Untagging socket 55\n01-22 20:19:51.515 I/Response(21800): success\n01-22 20:19:51.515 I/System.out(21800): (HTTPLog)-Static: isSBSettingEnabled false\n01-22 20:19:51.515 I/qtaguid (21800): Tagging socket 55 with tag 355f55a00000000{55965018,0} uid -1, pid: 21800, getuid(): 10522\n01-22 20:19:51.515 I/Response(21800): success\n01-22 20:19:51.515 I/System.out(21800): (HTTPLog)-Static: isSBSettingEnabled false\n01-22 20:19:51.515 I/qtaguid (21800): Tagging socket 75 with tag 355f55a00000000{55965018,0} uid -1, pid: 21800, getuid(): 10522\n01-22 20:19:51.515 I/qtaguid (21800): Untagging socket 71\n01-22 20:19:51.515 I/qtaguid (21800): Untagging socket 70\n01-22 20:19:51.515 I/Response(21800): success\n01-22 20:19:51.515 I/Response(21800): success\n01-22 20:19:51.525 I/qtaguid (21800): Untagging socket 75\n01-22 20:19:51.525 I/Response(21800): success\n01-22 20:19:51.535 I/qtaguid (21800): Untagging socket 55\n01-22 20:19:51.535 I/Response(21800): success\n01-22 20:19:53.005 D/ViewRootImpl(21800): ViewPostImeInputStage ACTION_DOWN\n01-22 20:19:53.175 D/AndroidRuntime(21800): Shutting down VM\n01-22 20:19:53.175 E/ACRA    (21800): ACRA caught a NullPointerException for com.android.logdata.logdata_android\n01-22 20:19:53.175 E/ACRA    (21800): java.lang.NullPointerException\n01-22 20:19:53.175 E/ACRA    (21800): \tat com.android.logdata.logdata_android.MainActivity$1.onClick(MainActivity.java:38)\n01-22 20:19:53.175 E/ACRA    (21800): \tat android.view.View.performClick(View.java:5194)\n01-22 20:19:53.175 E/ACRA    (21800): \tat android.view.View$PerformClick.run(View.java:20903)\n01-22 20:19:53.175 E/ACRA    (21800): \tat android.os.Handler.handleCallback(Handler.java:739)\n01-22 20:19:53.175 E/ACRA    (21800): \tat android.os.Handler.dispatchMessage(Handler.java:95)\n01-22 20:19:53.175 E/ACRA    (21800): \tat android.os.Looper.loop(Looper.java:145)\n01-22 20:19:53.175 E/ACRA    (21800): \tat android.app.ActivityThread.main(ActivityThread.java:5942)\n01-22 20:19:53.175 E/ACRA    (21800): \tat java.lang.reflect.Method.invoke(Native Method)\n01-22 20:19:53.175 E/ACRA    (21800): \tat java.lang.reflect.Method.invoke(Method.java:372)\n01-22 20:19:53.175 E/ACRA    (21800): \tat com.android.internal.os.ZygoteInit$MethodAndArgsCaller.run(ZygoteInit.java:1399)\n01-22 20:19:53.175 E/ACRA    (21800): \tat com.android.internal.os.ZygoteInit.main(ZygoteInit.java:1194)";

    @Test
    public void timeTranslateTest() {
        String timeTranslate = utility.getStringTimeToLong(1);

        assertThat(timeTranslate).isEqualTo(utility.getStringTimeToDateTime(new DateTime(1)));
    }

    @Test
    public void findCrashNameTest() {
        String crashName = utility.findCrashName(logcat);

        assertThat(crashName).isEqualTo("NullPointerException");
    }

    @Test
    public void findCrashNameNullTest() {
        String crashName = utility.findCrashName("");

        assertThat(crashName).isEqualTo("null");
    }

    @Test
    public void logcatSummaryTest() {
        String summaryData = "01-22 20:19:53.175 E/ACRA    (21800): ACRA caught a NullPointerException for com.android.logdata.logdata_android\n" +
                "01-22 20:19:53.175 E/ACRA    (21800): java.lang.NullPointerException\n" +
                "01-22 20:19:53.175 E/ACRA    (21800): \tat com.android.logdata.logdata_android.MainActivity$1.onClick(MainActivity.java:38)\n" +
                "01-22 20:19:53.175 E/ACRA    (21800): \tat android.view.View.performClick(View.java:5194)\n" +
                "01-22 20:19:53.175 E/ACRA    (21800): \tat android.view.View$PerformClick.run(View.java:20903)\n" +
                "01-22 20:19:53.175 E/ACRA    (21800): \tat android.os.Handler.handleCallback(Handler.java:739)\n" +
                "01-22 20:19:53.175 E/ACRA    (21800): \tat android.os.Handler.dispatchMessage(Handler.java:95)\n" +
                "01-22 20:19:53.175 E/ACRA    (21800): \tat android.os.Looper.loop(Looper.java:145)\n" +
                "01-22 20:19:53.175 E/ACRA    (21800): \tat android.app.ActivityThread.main(ActivityThread.java:5942)\n" +
                "01-22 20:19:53.175 E/ACRA    (21800): \tat java.lang.reflect.Method.invoke(Native Method)\n" +
                "01-22 20:19:53.175 E/ACRA    (21800): \tat java.lang.reflect.Method.invoke(Method.java:372)\n" +
                "01-22 20:19:53.175 E/ACRA    (21800): \tat com.android.internal.os.ZygoteInit$MethodAndArgsCaller.run(ZygoteInit.java:1399)\n" +
                "01-22 20:19:53.175 E/ACRA    (21800): \tat com.android.internal.os.ZygoteInit.main(ZygoteInit.java:1194)\n" +
                "";
        String summary = utility.logcatSummary(logcat);

        assertThat(summary).isEqualTo(summaryData);
    }
}