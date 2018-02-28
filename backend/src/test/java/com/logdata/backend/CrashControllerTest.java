package com.logdata.backend;

import com.logdata.backend.repository.CrashDataRepository;
import com.logdata.backend.repository.UserDataRepository;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CrashControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private CrashDataRepository crashDataRepository;
    @MockBean
    private UserDataRepository userDataRepository;

    @org.junit.Test
    public void crashDataListTest() throws Exception {
        UserVO user = new UserVO("user", "user");
        when(userDataRepository.findByUserID("user")).thenReturn(user);
        Set<String> set = new HashSet<>();
        // when
        MockHttpServletResponse response = mvc.perform(
                get("/crash")
                        .with(
                                user("user")
                                        .password("user")
                                        .roles("USER")
                        )
        ).andDo(print())
                .andExpect(view().name("crash"))
                .andExpect(model().attribute("noData", true))
                .andExpect(model().attribute("sideMenuItems", set))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentType()).isEqualTo(MediaType.TEXT_HTML_VALUE + ";charset=UTF-8");
    }

    @org.junit.Test
    public void crashDataListFailedTest() throws Exception {
        Set<String> set = new HashSet<>();
        // when
        MockHttpServletResponse response = mvc.perform(
                get("/crash")
//                        .with(
//                                user("user")
//                                        .password("user")
//                                        .roles("USER")
//                        )
        ).andDo(print())
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.FOUND.value());
        assertThat(response.getRedirectedUrl()).isEqualTo("http://localhost/login");
    }

    @Test
    public void crashDataTimeTest() {
        try {
            given(userDataRepository.findByUserID("user")).willReturn(new UserVO("user", "user"));
            given(crashDataRepository.findCrashDataByTimeAndApiKeyAndPackageName(new DateTime(1L), "apiKey", "android")).willReturn(new CrashVO());
            // when
            MockHttpServletResponse response = mvc.perform(
                    get("/crashtimefilter/query?")
                            .param("packageName", "com.android.logdata.logdata_android")
                            .param("time", "2018-02-18 12:05:17.780")
                            .with(
                                    user("user")
                                            .password("user")
                                            .roles("USER")
                            )
                            .contentType(MediaType.APPLICATION_JSON_UTF8)
            ).andDo(print())
                    .andExpect(view().name("nodata"))
                    .andReturn().getResponse();

            // then
            assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
            assertThat(response.getContentType()).isEqualTo(MediaType.TEXT_HTML_VALUE + ";charset=UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void crashDataPackageNameFilterTest() {
        try {
            // when
            MockHttpServletResponse response = mvc.perform(
                    get("/crashpackagenamefilter/query?")
                            .param("packageName", "com.android.logdata.logdata_android")
                            .with(
                                    user("user")
                                            .password("user")
                                            .roles("USER")
                            )
                            .contentType(MediaType.APPLICATION_JSON_UTF8)
            ).andDo(print())
                    .andExpect(model().attribute("noData", true))
                    .andReturn().getResponse();

            // then
            assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
            assertThat(response.getContentType()).isEqualTo(MediaType.TEXT_HTML_VALUE + ";charset=UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void crashDataPostTest() {
        try {
            MockHttpServletResponse response = mvc.perform(
                    post("/crash")
                            .header("secretKey", "apiKey")
                            .with(
                                    user("user")
                                            .password("user")
                                            .roles("USER")
                            )
                            .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content("{\n" +
                            "\t\"packageName\" : \"android3\",\n" +
                            "    \"time\" : 1626620994631,\n" +
                            "    \"androidVersion\" : \"5.0\",\n" +
                            "    \"appVersionCode\" : \"1\",\n" +
                            "    \"appVersionName\" : \"1.0\",\n" +
                            "    \"availableMemorySize\" : 1309798400,\n" +
                            "    \"brand\" : \"samsung\",\n" +
                            "    \"logcat\" : \"01-22 20:19:51.195 D/[MainActivity::onCreate](21800): debug\\n01-22 20:19:51.195 I/System.out(21800): (HTTPLog)-Static: isSBSettingEnabled false\\n01-22 20:19:51.195 W/[MainActivity::onCreate](21800): warning\\n01-22 20:19:51.195 I/System.out(21800): (HTTPLog)-Static: isShipBuild true\\n01-22 20:19:51.195 I/System.out(21800): (HTTPLog)-Thread-140221-34964680: SmartBonding Enabling is false, SHIP_BUILD is true, log to file is false, DBG is false\\n01-22 20:19:51.195 I/System.out(21800): (HTTPLog)-Static: isSBSettingEnabled false\\n01-22 20:19:51.195 V/[MainActivity::onCreate](21800): verb\\n01-22 20:19:51.195 D/Activity(21800): performCreate Call secproduct feature valuefalse\\n01-22 20:19:51.195 D/Activity(21800): performCreate Call debug elastic valuetrue\\n01-22 20:19:51.195 I/System.out(21800): KnoxVpnUidStorageknoxVpnSupported API value returned is false\\n01-22 20:19:51.195 I/qtaguid (21800): Tagging socket 55 with tag 355f55a00000000{55965018,0} uid -1, pid: 21800, getuid(): 10522\\n01-22 20:19:51.215 D/OpenGLRenderer(21800): Render dirty regions requested: true\\n01-22 20:19:51.285 I/Adreno-EGL(21800): <qeglDrvAPI_eglInitialize:410>: EGL 1.4 QUALCOMM build: AU_LINUX_ANDROID_LA.BF.1.1_RB1.05.00.00.002.025_msm8974_LA.BF.1.1_RB1__release_AU ()\\n01-22 20:19:51.285 I/Adreno-EGL(21800): OpenGL ES Shader Compiler Version: E031.25.01.03\\n01-22 20:19:51.285 I/Adreno-EGL(21800): Build Date: 11/19/14 Wed\\n01-22 20:19:51.285 I/Adreno-EGL(21800): Local Branch: mybranch5813579\\n01-22 20:19:51.285 I/Adreno-EGL(21800): Remote Branch: quic/LA.BF.1.1_rb1.11\\n01-22 20:19:51.285 I/Adreno-EGL(21800): Local Patches: NONE\\n01-22 20:19:51.285 I/Adreno-EGL(21800): Reconstruct Branch: AU_LINUX_ANDROID_LA.BF.1.1_RB1.05.00.00.002.025 + 30e7589 +  NOTHING\\n01-22 20:19:51.285 I/OpenGLRenderer(21800): Initialized EGL, version 1.4\\n01-22 20:19:51.305 I/OpenGLRenderer(21800): HWUI protection enabled for context ,  &this =0xae994218 ,&mEglDisplay = 1 , &mEglConfig = 8 \\n01-22 20:19:51.305 D/OpenGLRenderer(21800): Enabling debug mode 0\\n01-22 20:19:51.345 I/qtaguid (21800): Untagging socket 55\\n01-22 20:19:51.375 W/art     (21800): Before Android 4.1, method int android.support.v7.widget.ListViewCompat.lookForSelectablePosition(int, boolean) would have incorrectly overridden the package-private method in android.widget.ListView\\n01-22 20:19:51.425 I/Response(21800): success\\n01-22 20:19:51.435 I/System.out(21800): (HTTPLog)-Static: isSBSettingEnabled false\\n01-22 20:19:51.435 I/qtaguid (21800): Tagging socket 55 with tag 355f55a00000000{55965018,0} uid -1, pid: 21800, getuid(): 10522\\n01-22 20:19:51.445 I/System.out(21800): (HTTPLog)-Static: isSBSettingEnabled false\\n01-22 20:19:51.445 I/System.out(21800): KnoxVpnUidStorageknoxVpnSupported API value returned is false\\n01-22 20:19:51.445 I/Timeline(21800): Timeline: Activity_idle id: android.os.BinderProxy@188dd2e3 time:1082145136\\n01-22 20:19:51.445 I/System.out(21800): (HTTPLog)-Static: isSBSettingEnabled false\\n01-22 20:19:51.445 I/qtaguid (21800): Tagging socket 70 with tag 355f55a00000000{55965018,0} uid -1, pid: 21800, getuid(): 10522\\n01-22 20:19:51.445 I/System.out(21800): KnoxVpnUidStorageknoxVpnSupported API value returned is false\\n01-22 20:19:51.445 I/qtaguid (21800): Tagging socket 71 with tag 355f55a00000000{55965018,0} uid -1, pid: 21800, getuid(): 10522\\n01-22 20:19:51.455 I/System.out(21800): (HTTPLog)-Static: isSBSettingEnabled false\\n01-22 20:19:51.455 I/System.out(21800): KnoxVpnUidStorageknoxVpnSupported API value returned is false\\n01-22 20:19:51.455 I/qtaguid (21800): Tagging socket 75 with tag 355f55a00000000{55965018,0} uid -1, pid: 21800, getuid(): 10522\\n01-22 20:19:51.455 I/qtaguid (21800): Untagging socket 55\\n01-22 20:19:51.465 I/qtaguid (21800): Untagging socket 70\\n01-22 20:19:51.465 I/qtaguid (21800): Untagging socket 71\\n01-22 20:19:51.465 I/qtaguid (21800): Untagging socket 75\\n01-22 20:19:51.475 I/Response(21800): success\\n01-22 20:19:51.475 I/System.out(21800): (HTTPLog)-Static: isSBSettingEnabled false\\n01-22 20:19:51.475 I/qtaguid (21800): Tagging socket 55 with tag 355f55a00000000{55965018,0} uid -1, pid: 21800, getuid(): 10522\\n01-22 20:19:51.475 I/Response(21800): success\\n01-22 20:19:51.475 I/Response(21800): success\\n01-22 20:19:51.475 I/Response(21800): success\\n01-22 20:19:51.475 I/System.out(21800): (HTTPLog)-Static: isSBSettingEnabled false\\n01-22 20:19:51.475 I/qtaguid (21800): Tagging socket 70 with tag 355f55a00000000{55965018,0} uid -1, pid: 21800, getuid(): 10522\\n01-22 20:19:51.475 I/System.out(21800): (HTTPLog)-Static: isSBSettingEnabled false\\n01-22 20:19:51.475 I/System.out(21800): (HTTPLog)-Static: isSBSettingEnabled false\\n01-22 20:19:51.475 I/qtaguid (21800): Tagging socket 71 with tag 355f55a00000000{55965018,0} uid -1, pid: 21800, getuid(): 10522\\n01-22 20:19:51.475 I/qtaguid (21800): Tagging socket 75 with tag 355f55a00000000{55965018,0} uid -1, pid: 21800, getuid(): 10522\\n01-22 20:19:51.485 I/qtaguid (21800): Untagging socket 55\\n01-22 20:19:51.495 I/Response(21800): success\\n01-22 20:19:51.495 I/System.out(21800): (HTTPLog)-Static: isSBSettingEnabled false\\n01-22 20:19:51.495 I/qtaguid (21800): Tagging socket 55 with tag 355f55a00000000{55965018,0} uid -1, pid: 21800, getuid(): 10522\\n01-22 20:19:51.495 I/qtaguid (21800): Untagging socket 75\\n01-22 20:19:51.495 I/Response(21800): success\\n01-22 20:19:51.495 I/System.out(21800): (HTTPLog)-Static: isSBSettingEnabled false\\n01-22 20:19:51.495 I/qtaguid (21800): Untagging socket 71\\n01-22 20:19:51.495 I/qtaguid (21800): Tagging socket 75 with tag 355f55a00000000{55965018,0} uid -1, pid: 21800, getuid(): 10522\\n01-22 20:19:51.495 I/qtaguid (21800): Untagging socket 70\\n01-22 20:19:51.495 I/Response(21800): success\\n01-22 20:19:51.495 I/System.out(21800): (HTTPLog)-Static: isSBSettingEnabled false\\n01-22 20:19:51.495 I/qtaguid (21800): Tagging socket 71 with tag 355f55a00000000{55965018,0} uid -1, pid: 21800, getuid(): 10522\\n01-22 20:19:51.505 I/Response(21800): success\\n01-22 20:19:51.505 I/System.out(21800): (HTTPLog)-Static: isSBSettingEnabled false\\n01-22 20:19:51.505 I/qtaguid (21800): Tagging socket 70 with tag 355f55a00000000{55965018,0} uid -1, pid: 21800, getuid(): 10522\\n01-22 20:19:51.505 I/qtaguid (21800): Untagging socket 75\\n01-22 20:19:51.505 I/qtaguid (21800): Untagging socket 55\\n01-22 20:19:51.515 I/Response(21800): success\\n01-22 20:19:51.515 I/System.out(21800): (HTTPLog)-Static: isSBSettingEnabled false\\n01-22 20:19:51.515 I/qtaguid (21800): Tagging socket 55 with tag 355f55a00000000{55965018,0} uid -1, pid: 21800, getuid(): 10522\\n01-22 20:19:51.515 I/Response(21800): success\\n01-22 20:19:51.515 I/System.out(21800): (HTTPLog)-Static: isSBSettingEnabled false\\n01-22 20:19:51.515 I/qtaguid (21800): Tagging socket 75 with tag 355f55a00000000{55965018,0} uid -1, pid: 21800, getuid(): 10522\\n01-22 20:19:51.515 I/qtaguid (21800): Untagging socket 71\\n01-22 20:19:51.515 I/qtaguid (21800): Untagging socket 70\\n01-22 20:19:51.515 I/Response(21800): success\\n01-22 20:19:51.515 I/Response(21800): success\\n01-22 20:19:51.525 I/qtaguid (21800): Untagging socket 75\\n01-22 20:19:51.525 I/Response(21800): success\\n01-22 20:19:51.535 I/qtaguid (21800): Untagging socket 55\\n01-22 20:19:51.535 I/Response(21800): success\\n01-22 20:19:53.005 D/ViewRootImpl(21800): ViewPostImeInputStage ACTION_DOWN\\n01-22 20:19:53.175 D/AndroidRuntime(21800): Shutting down VM\\n01-22 20:19:53.175 E/ACRA    (21800): ACRA caught a NullPointerException for com.android.logdata.logdata_android\\n01-22 20:19:53.175 E/ACRA    (21800): java.lang.NullPointerException\\n01-22 20:19:53.175 E/ACRA    (21800): \\tat com.android.logdata.logdata_android.MainActivity$1.onClick(MainActivity.java:38)\\n01-22 20:19:53.175 E/ACRA    (21800): \\tat android.view.View.performClick(View.java:5194)\\n01-22 20:19:53.175 E/ACRA    (21800): \\tat android.view.View$PerformClick.run(View.java:20903)\\n01-22 20:19:53.175 E/ACRA    (21800): \\tat android.os.Handler.handleCallback(Handler.java:739)\\n01-22 20:19:53.175 E/ACRA    (21800): \\tat android.os.Handler.dispatchMessage(Handler.java:95)\\n01-22 20:19:53.175 E/ACRA    (21800): \\tat android.os.Looper.loop(Looper.java:145)\\n01-22 20:19:53.175 E/ACRA    (21800): \\tat android.app.ActivityThread.main(ActivityThread.java:5942)\\n01-22 20:19:53.175 E/ACRA    (21800): \\tat java.lang.reflect.Method.invoke(Native Method)\\n01-22 20:19:53.175 E/ACRA    (21800): \\tat java.lang.reflect.Method.invoke(Method.java:372)\\n01-22 20:19:53.175 E/ACRA    (21800): \\tat com.android.internal.os.ZygoteInit$MethodAndArgsCaller.run(ZygoteInit.java:1399)\\n01-22 20:19:53.175 E/ACRA    (21800): \\tat com.android.internal.os.ZygoteInit.main(ZygoteInit.java:1194)\",\n" +
                            "    \"display\" : {\n" +
                            "            \"0\" : {\n" +
                            "                    \"currentSizeRange\" : {\n" +
                            "                            \"smallest\" : [\n" +
                            "                                    1080,\n" +
                            "                                    1005\n" +
                            "                            ],\n" +
                            "                            \"largest\" : [\n" +
                            "                                    1920,\n" +
                            "                                    1845\n" +
                            "                            ]\n" +
                            "                    },\n" +
                            "                    \"flags\" : \"FLAG_SUPPORTS_PROTECTED_BUFFERS+FLAG_SECURE\",\n" +
                            "                    \"metrics\" : {\n" +
                            "                            \"density\" : 3,\n" +
                            "                            \"densityDpi\" : 480,\n" +
                            "                            \"scaledDensity\" : \"x3.0\",\n" +
                            "                            \"widthPixels\" : 1080,\n" +
                            "                            \"heightPixels\" : 1920,\n" +
                            "                            \"xdpi\" : 386.3659973144531,\n" +
                            "                            \"ydpi\" : 387.0469970703125\n" +
                            "                    },\n" +
                            "                    \"realMetrics\" : {\n" +
                            "                            \"density\" : 3,\n" +
                            "                            \"densityDpi\" : 480,\n" +
                            "                            \"scaledDensity\" : \"x3.0\",\n" +
                            "                            \"widthPixels\" : 1080,\n" +
                            "                            \"heightPixels\" : 1920,\n" +
                            "                            \"xdpi\" : 386.3659973144531,\n" +
                            "                            \"ydpi\" : 387.0469970703125\n" +
                            "                    },\n" +
                            "                    \"name\" : \"기본으로 제공되는 화면\",\n" +
                            "                    \"realSize\" : [\n" +
                            "                            1080,\n" +
                            "                            1920\n" +
                            "                    ],\n" +
                            "                    \"rectSize\" : [\n" +
                            "                            0,\n" +
                            "                            0,\n" +
                            "                            1080,\n" +
                            "                            1920\n" +
                            "                    ],\n" +
                            "                    \"size\" : [\n" +
                            "                            1080,\n" +
                            "                            1920\n" +
                            "                    ],\n" +
                            "                    \"rotation\" : \"ROTATION_0\",\n" +
                            "                    \"isValid\" : true,\n" +
                            "                    \"orientation\" : 0,\n" +
                            "                    \"refreshRate\" : 60,\n" +
                            "                    \"height\" : 1920,\n" +
                            "                    \"width\" : 1080,\n" +
                            "                    \"pixelFormat\" : 1\n" +
                            "            }\n" +
                            "    },\n" +
                            "    \"environment\" : {\n" +
                            "            \"getAndroidSecureContainerDirectory\" : \"/mnt/asec\",\n" +
                            "            \"getDataDirectory\" : \"/data\",\n" +
                            "            \"getDownloadCacheDirectory\" : \"/cache\",\n" +
                            "            \"getEmulatedStorageObbSource\" : \"/mnt/shell/emulated/obb\",\n" +
                            "            \"getExternalStorageDirectory\" : \"/storage/emulated/0\",\n" +
                            "            \"getExternalStorageState\" : \"mounted\",\n" +
                            "            \"getLegacyExternalStorageDirectory\" : \"/storage/emulated/legacy\",\n" +
                            "            \"getLegacyExternalStorageObbDirectory\" : \"/storage/emulated/legacy/Android/obb\",\n" +
                            "            \"getMediaBaseStorageDirectory\" : \"/secdata\",\n" +
                            "            \"getMediaStorageDirectory\" : \"/data/media/0\",\n" +
                            "            \"getOemDirectory\" : \"/oem\",\n" +
                            "            \"getRootDirectory\" : \"/system\",\n" +
                            "            \"getSecureDataDirectory\" : \"/data\",\n" +
                            "            \"getSystemSecureDirectory\" : \"/data/system\",\n" +
                            "            \"getVendorDirectory\" : \"/vendor\",\n" +
                            "            \"isEncryptedFilesystemEnabled\" : false,\n" +
                            "            \"isExternalStorageEmulated\" : true,\n" +
                            "            \"isExternalStorageRemovable\" : false\n" +
                            "    },\n" +
                            "    \"deviceFeatures\" : {\n" +
                            "            \"android-hardware-wifi-direct\" : true,\n" +
                            "            \"android-hardware-touchscreen-multitouch\" : true,\n" +
                            "            \"com-sec-feature-cover-flip\" : true,\n" +
                            "            \"com-sec-android-smartface-smart_pause\" : true,\n" +
                            "            \"com-sec-feature-multiwindow-recentui\" : true,\n" +
                            "            \"android-hardware-sensor-stepcounter\" : true,\n" +
                            "            \"android-hardware-sensor-proximity\" : true,\n" +
                            "            \"android-hardware-telephony\" : true,\n" +
                            "            \"com-sec-feature-hovering_ui\" : true,\n" +
                            "            \"android-hardware-bluetooth_le\" : true,\n" +
                            "            \"com-sec-android-mdm\" : true,\n" +
                            "            \"com-sec-feature-motionrecognition_service\" : true,\n" +
                            "            \"android-hardware-usb-host\" : true,\n" +
                            "            \"com-sec-android-secimaging\" : true,\n" +
                            "            \"android-hardware-sensor-stepdetector\" : true,\n" +
                            "            \"com-sec-feature-wfd_support\" : true,\n" +
                            "            \"android-software-voice_recognizers\" : true,\n" +
                            "            \"com-sec-feature-multiwindow-phone\" : true,\n" +
                            "            \"android-hardware-location-gps\" : true,\n" +
                            "            \"com-sec-android-smartface-smart_stay\" : true,\n" +
                            "            \"com-sec-feature-multiwindow-multiwindowlaunch\" : true,\n" +
                            "            \"android-hardware-sensor-gyroscope\" : true,\n" +
                            "            \"com-sec-feature-barcode_emulator\" : true,\n" +
                            "            \"com-sec-feature-sensorhub\" : true,\n" +
                            "            \"android-software-sip\" : true,\n" +
                            "            \"android-hardware-sensor-ambient_temperature\" : true,\n" +
                            "            \"android-software-input_methods\" : true,\n" +
                            "            \"android-software-connectionservice\" : true,\n" +
                            "            \"android-software-webview\" : true,\n" +
                            "            \"android-software-backup\" : true,\n" +
                            "            \"com-sec-feature-multiwindow-styletransition\" : true,\n" +
                            "            \"com-sec-feature-call_vt_support\" : true,\n" +
                            "            \"android-hardware-nfc-hce\" : true,\n" +
                            "            \"android-hardware-camera-front\" : true,\n" +
                            "            \"android-hardware-screen-portrait\" : true,\n" +
                            "            \"android-hardware-touchscreen\" : true,\n" +
                            "            \"com-sec-feature-cover-sview\" : true,\n" +
                            "            \"android-software-live_wallpaper\" : true,\n" +
                            "            \"com-sec-feature-cover-sviewcover\" : true,\n" +
                            "            \"com-sec-feature-spen_usp\" : true,\n" +
                            "            \"android-hardware-wifi\" : true,\n" +
                            "            \"android-software-print\" : true,\n" +
                            "            \"com-sec-feature-findo\" : true,\n" +
                            "            \"com-sec-feature-multiwindow\" : true,\n" +
                            "            \"com-samsung-android-feature-sdl-2101\" : true,\n" +
                            "            \"android-hardware-sensor-accelerometer\" : true,\n" +
                            "            \"android-software-app_widgets\" : true,\n" +
                            "            \"android-software-device_admin\" : true,\n" +
                            "            \"android-hardware-camera-any\" : true,\n" +
                            "            \"android-software-sip-voip\" : true,\n" +
                            "            \"com-sec-feature-multiwindow-scalewindow\" : true,\n" +
                            "            \"android-hardware-camera\" : true,\n" +
                            "            \"com-sec-feature-multiwindow-multiinstance\" : true,\n" +
                            "            \"android-hardware-usb-accessory\" : true,\n" +
                            "            \"android-hardware-touchscreen-multitouch-distinct\" : true,\n" +
                            "            \"com-sec-feature-multiwindow-freestylelaunch\" : true,\n" +
                            "            \"com-sec-feature-multiwindow-freestyle\" : true,\n" +
                            "            \"com-sec-feature-minimode\" : true,\n" +
                            "            \"com-sec-feature-multiwindow-commonui\" : true,\n" +
                            "            \"android-hardware-consumerir\" : true,\n" +
                            "            \"android-hardware-camera-flash\" : true,\n" +
                            "            \"android-hardware-faketouch\" : true,\n" +
                            "            \"android-hardware-sensor-barometer\" : true,\n" +
                            "            \"android-hardware-sensor-relative_humidity\" : true,\n" +
                            "            \"android-hardware-sensor-light\" : true,\n" +
                            "            \"android-hardware-sensor-compass\" : true,\n" +
                            "            \"android-hardware-location\" : true,\n" +
                            "            \"android-hardware-screen-landscape\" : true,\n" +
                            "            \"com-sec-feature-samsunglinkplatform\" : true,\n" +
                            "            \"android-hardware-location-network\" : true,\n" +
                            "            \"android-hardware-telephony-gsm\" : true,\n" +
                            "            \"android-software-managed_users\" : true,\n" +
                            "            \"android-hardware-bluetooth\" : true,\n" +
                            "            \"android-hardware-audio-output\" : true,\n" +
                            "            \"android-hardware-nfc\" : true,\n" +
                            "            \"com-sec-android-smartface-smart_rotation\" : true,\n" +
                            "            \"android-software-home_screen\" : true,\n" +
                            "            \"com-sec-feature-multiwindow-minimizeanimation\" : true,\n" +
                            "            \"android-hardware-touchscreen-multitouch-jazzhand\" : true,\n" +
                            "            \"android-hardware-microphone\" : true,\n" +
                            "            \"com-sec-feature-multiwindow-minimize\" : true,\n" +
                            "            \"android-hardware-camera-autofocus\" : true,\n" +
                            "            \"glEsVersion\" : \"3.0\"\n" +
                            "    },\n" +
                            "    \"build\" : {\n" +
                            "            \"BOARD\" : \"MSM8974\",\n" +
                            "            \"BOOTLOADER\" : \"N900SKSU0GPI1\",\n" +
                            "            \"BRAND\" : \"samsung\",\n" +
                            "            \"CPU_ABI\" : \"armeabi-v7a\",\n" +
                            "            \"CPU_ABI2\" : \"armeabi\",\n" +
                            "            \"DEVICE\" : \"hlteskt\",\n" +
                            "            \"DISPLAY\" : \"LRX21V.N900SKSU0GPI1\",\n" +
                            "            \"FINGERPRINT\" : \"samsung/hlteskt/hlteskt:5.0/LRX21V/N900SKSU0GPI1:user/release-keys\",\n" +
                            "            \"FOTA_INFO\" : \"1473078735\",\n" +
                            "            \"HARDWARE\" : \"qcom\",\n" +
                            "            \"HOST\" : \"SWDD6823\",\n" +
                            "            \"ID\" : \"LRX21V\",\n" +
                            "            \"MANUFACTURER\" : \"samsung\",\n" +
                            "            \"MODEL\" : \"SM-N900S\",\n" +
                            "            \"PRODUCT\" : \"hlteskt\",\n" +
                            "            \"RADIO\" : \"unknown\",\n" +
                            "            \"SERIAL\" : \"b72860c2\",\n" +
                            "            \"SUPPORTED_32_BIT_ABIS\" : [\n" +
                            "                    \"armeabi-v7a\",\n" +
                            "                    \"armeabi\"\n" +
                            "            ],\n" +
                            "            \"SUPPORTED_64_BIT_ABIS\" : [ ],\n" +
                            "            \"SUPPORTED_ABIS\" : [\n" +
                            "                    \"armeabi-v7a\",\n" +
                            "                    \"armeabi\"\n" +
                            "            ],\n" +
                            "            \"TAGS\" : \"release-keys\",\n" +
                            "            \"TYPE\" : \"user\",\n" +
                            "            \"UNKNOWN\" : \"unknown\",\n" +
                            "            \"USER\" : \"dpi\",\n" +
                            "            \"TIME\" : \"1473078810000\",\n" +
                            "            \"IS_DEBUGGABLE\" : false,\n" +
                            "            \"IS_SYSTEM_SECURE\" : false,\n" +
                            "            \"isOSUpgradeKK2LL\" : true,\n" +
                            "            \"VERSION\" : {\n" +
                            "                    \"ACTIVE_CODENAMES\" : [ ],\n" +
                            "                    \"BASE_OS\" : \"\",\n" +
                            "                    \"CODENAME\" : \"REL\",\n" +
                            "                    \"INCREMENTAL\" : \"N900SKSU0GPI1\",\n" +
                            "                    \"RELEASE\" : \"5.0\",\n" +
                            "                    \"SDK\" : \"21\",\n" +
                            "                    \"SECURITY_PATCH\" : \"2016-09-01\",\n" +
                            "                    \"RESOURCES_SDK_INT\" : 21,\n" +
                            "                    \"SDK_INT\" : 21,\n" +
                            "                    \"SDL_INT\" : 2101\n" +
                            "            }\n" +
                            "    }\n" +
                            "}")
            ).andDo(print())
                    .andReturn().getResponse();

            // then
            assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
            assertThat(response.getContentType()).isEqualTo(MediaType.APPLICATION_JSON_UTF8.toString());
            assertThat(response.getContentAsString()).isEqualTo("{\"result\":\"Crash Data Transfer Success\"}");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @org.junit.Test
    public void crashDataPostButNotAPIKeyTest() {
        try {
            MockHttpServletResponse response = mvc.perform(post("/crash").with(user("user").password("user").roles("USER")))
                    .andDo(print())
                    .andReturn().getResponse();

            assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
            assertThat(response.getErrorMessage()).isEqualTo("Missing request header 'secretKey' for method parameter of type String");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
