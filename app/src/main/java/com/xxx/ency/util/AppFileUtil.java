package com.xxx.ency.util;

import android.content.Context;
import android.os.Environment;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

/**
 * Created by xiarh on 2017/9/14.
 */

public class AppFileUtil {

    /**
     * 检查是否已挂载SD卡（是否存在SD卡）
     *
     * @return
     */
    public static boolean isMountedSDCard() {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            return true;
        } else {
            LogUtil.i("未找到SD Card！");
            return false;
        }
    }

    /**
     * 创建文件夹(File.separator 系统分隔符 "/")
     *
     * @param context
     * @param dirName 文件夹名称
     * @return 返回文件
     */
    public static File createFileDir(Context context, String dirName) {
        String filePath;
        // 如SD卡已存在，则存储；反之存在data目录下
        if (isMountedSDCard()) {
            // SD卡路径
            filePath = Environment.getExternalStorageDirectory() + File.separator + dirName;
        } else {
            // 内部存储路径
            filePath = context.getFilesDir().getPath() + File.separator + dirName;
        }
        File destDir = new File(filePath);
        if (!destDir.exists()) {
            boolean isCreate = destDir.mkdirs();
            LogUtil.i(filePath + " 创建" + isCreate);
        }
        return destDir;
    }

    /**
     * 创建文件(指定路径创建)
     *
     * @param parentFilePath 父文件夹路径
     * @param fileName       文件名带后缀
     * @return
     */
    public static File createFile(String parentFilePath, String fileName) {
        File dir = new File(parentFilePath + File.separator + fileName);
        if (!dir.exists()) {
            try {
                //在指定的文件夹中创建文件
                dir.createNewFile();
            } catch (Exception e) {
                LogUtil.e("创建失败");
            }
        }
        return dir;
    }

    /**
     * 创建文件
     *
     * @param context
     * @param fileName 文件名带后缀
     * @return
     */
    public static File createFile(Context context, String fileName) {
        String filePath;
        if (isMountedSDCard()) {
            filePath = Environment.getExternalStorageDirectory() + File.separator + fileName;
        } else {
            filePath = context.getFilesDir().getPath() + File.separator + fileName;
        }
        File dir = new File(filePath);
        if (!dir.exists()) {
            try {
                //在指定的文件夹中创建文件
                dir.createNewFile();
            } catch (Exception e) {
                LogUtil.e("创建失败");
            }
        }
        return dir;
    }

    /**
     * 判断文件是否存在
     *
     * @param filePath 文件路径
     * @return 是否存在
     * @throws Exception
     */
    public static Boolean isExsit(String filePath) {
        Boolean flag = false;
        try {
            File file = new File(filePath);
            if (file.exists()) {
                flag = true;
            }
        } catch (Exception e) {
            LogUtil.e("判断文件失败");
        }
        return flag;
    }

    /**
     * 删除文件（若为目录，则递归删除子目录和文件）
     *
     * @param file
     * @param delThisPath true代表删除参数指定file，false代表保留参数指定file
     */
    public static void delFile(File file, boolean delThisPath) {
        if (!file.exists()) {
            return;
        }
        if (file.isDirectory()) {
            File[] subFiles = file.listFiles();
            if (subFiles != null) {
                int num = subFiles.length;
                // 删除子目录和文件
                for (int i = 0; i < num; i++) {
                    delFile(subFiles[i], true);
                }
                LogUtil.i(file.getName() + "子文件删除>>>");
            }
        }
        if (delThisPath) {
            file.delete();
            LogUtil.i(file.getName() + "删除>>>");
        }
    }

    /**
     * 获取文件大小，单位为byte（若为目录，则包括所有子目录和文件）
     *
     * @param file
     * @return
     */
    public static long getFileSize(File file) {
        long size = 0;
        if (file.exists()) {
            if (file.isDirectory()) {
                File[] subFiles = file.listFiles();
                if (subFiles != null) {
                    int num = subFiles.length;
                    for (int i = 0; i < num; i++) {
                        size += getFileSize(subFiles[i]);
                    }
                }
            } else {
                size += file.length();
            }
        }
        return size;
    }

    /**
     * 保存内容
     *
     * @param file    文件源
     * @param content 保存的内容
     * @throws IOException
     */
    public static void saveToFile(File file, String content)  {
        saveToFile(file, content, System.getProperty("file.encoding"));
    }

    /**
     * 指定编码保存内容
     *
     * @param file     文件源
     * @param content  保存的内容
     * @param encoding 写文件编码
     * @throws IOException
     */
    public static void saveToFile(File file, String content, String encoding)  {
        BufferedWriter writer = null;
        try {
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, false), encoding));
            writer.write(content);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 追加文本
     *
     * @param content 需要追加的内容
     * @param file    待追加文件源
     * @throws IOException
     */
    public static void appendToFile(File file, String content) {
        appendToFile(file, content, System.getProperty("file.encoding"));
    }

    /**
     * 追加文本
     *
     * @param content  需要追加的内容
     * @param file     待追加文件源
     * @param encoding 文件编码
     * @throws IOException
     */
    public static void appendToFile(File file, String content, String encoding)  {
        BufferedWriter writer = null;
        try {
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true), encoding));
            writer.write(content);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 读取assets目录的文件内容
     *
     * @param context  内容上下文
     * @param fileName 文件名称，包含扩展名
     * @return
     */
    public static String readAssetsValue(Context context, String fileName) {
        String result = "";
        try {
            InputStream is = context.getResources().getAssets().open(fileName);
            int len = is.available();
            byte[] buffer = new byte[len];
            is.read(buffer);
            result = new String(buffer, "UTF-8");
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
