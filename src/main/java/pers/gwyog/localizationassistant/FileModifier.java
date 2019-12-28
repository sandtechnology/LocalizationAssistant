package pers.gwyog.localizationassistant;

import pers.gwyog.localizationassistant.filesAdapter.AdapterSelector;
import pers.gwyog.localizationassistant.filesAdapter.LangDataMap;

import javax.swing.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileModifier {

    /*
     * LA所有的功能都由此类的实例对象实现
     * 
     * @Author: JJN(GWYOG)
     */

    private MainFrame parentFrame;
    private FileInputStream fis;
    private InputStreamReader isr;
    private static Pattern chinesePattern = Pattern.compile("[\u4e00-\u9fbf]");
    private File file;
    private FileOutputStream fos;
    private BufferedReader bufferedReader;

    private String fileInput1;
    private String fileInput2;
    private String fileInput3;
    private String fileOutput;
    private String sOrigin;
    private String sTo;
    private String sInterval;
    private String sAdd;
    private String sRemoveFlag;
    private String[] keyFilter;
    private String[] rowNumberSplit;
    private String[] specialIgnoreSymbol;
    private String[] filter;
    private int updateType;

    // 用作全局变量
    private List<String> commentList = new LinkedList<>();
    private OutputStreamWriter outputStreamWriter;

    public FileModifier(MainFrame parentFrame, int functionNumber, String fileInput, String fileOutput, String str1,
            String str2, String[] keyFilter, String[] rowNumberSplit, String[] specialIgnoreSymbol) {
        this.parentFrame = parentFrame;
        this.fileInput1 = fileInput;
        this.fileOutput = fileOutput;
        switch (functionNumber) {
        case 1:
            this.sOrigin = str1;
            this.sTo = str2;
            break;
        case 2:
            this.sInterval = str1;
            this.sAdd = str2;
            this.keyFilter = keyFilter;
            break;
        }
        this.rowNumberSplit = rowNumberSplit;
        this.specialIgnoreSymbol = specialIgnoreSymbol;
    }

    public FileModifier(MainFrame parentFrame, String fileInput1, String fileInput2, String fileInput3,
            String fileOutput, int updateType) {
        this.parentFrame = parentFrame;
        this.fileInput1 = fileInput1;
        this.fileInput2 = fileInput2;
        this.fileInput3 = fileInput3;
        this.fileOutput = fileOutput;
        this.updateType = updateType;
    }

    public FileModifier(MainFrame parentFrame, String fileInput1, String fileOutput, String sRemoveFlag,
			String[] rowNumberSplit, int flag) {
        this.parentFrame = parentFrame;
        this.fileInput1 = fileInput1;
        this.fileOutput = fileOutput;
        this.sRemoveFlag = sRemoveFlag;
        this.rowNumberSplit = rowNumberSplit;
        this.updateType = flag;
	}

    public FileModifier(MainFrame parentFrame, String fileInput1, String fileOutput, String[] filter) {
        this.parentFrame = parentFrame;
        this.fileInput1 = fileInput1;
        this.fileOutput = fileOutput;
        this.filter = filter;
    }

	// 将文件路径变为缓存文件路径
    public String dirToTempDir(String directory) {
        return directory.substring(0, directory.lastIndexOf('.')) + "-Temp.lang";
    }

    // 输入一个int型参数，判断它是否在用户给定的操作行数范围内：不在的话返回-1，在的话返回该行数，如果用户未指定操作行数的话，默认对所有行操作，此时返回0
    public int verifyLineNumber(int lineNumber) {
        if (rowNumberSplit[0].equals("Blank"))
            return 0;
        else
            for (int index = 0; index <= rowNumberSplit.length - 1; index++) {
                try {
                    int index2 = rowNumberSplit[index].indexOf("-");
                    if (index2 == -1 && lineNumber == Integer.parseInt(rowNumberSplit[index])) {
                        return lineNumber;
                    } else if (index2 != -1
                            && lineNumber >= Integer.parseInt(rowNumberSplit[index].substring(0, index2))
                            && lineNumber <= Integer.parseInt(rowNumberSplit[index].substring(index2 + 1))) {
                        return lineNumber;

                    }

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "行数格式输入错误！", "错误", JOptionPane.ERROR_MESSAGE);
                }
            }
        return -1;
    }

    // 判断String类型的字符串能否转化为整数，如果能则返回该整数，不然则返回-1
    public int verifyNumber(String number) {
        try {
            return Integer.parseInt(number);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "数字格式输入错误！", "错误", JOptionPane.ERROR_MESSAGE);
        }
        return -1;
    }

    private static void createIfNotExist(String... paths) throws IOException {
        for (String path : paths) {
            if (Files.notExists(Paths.get(path))) {
                Files.createFile(Paths.get(path));
            }
        }
    }

    /***
     *
     * 条件替换
     *
     * @param parent 主界面引用
     * @param input 输入文件的路径
     * @param output 输出文件的路径
     * @param replace 查找和替换的内容
     * @param filters 排除集合
     */
    public static void conditionalReplace(MainFrame parent, String input, String output, StartAndEndHolder replace, StartAndEndHolder... filters) {
        MessageWindow processing = new MessageWindow(parent, "处理中", "", 0);
        try {
            LangDataMap<String, String> dataMap = AdapterSelector.read(Paths.get(input));
            dataMap.entrySet().forEach(e -> {
                String content = e.getValue();
                //无过滤器时跳过
                if (filters[0] == null) {
                    content = content.replaceAll(replace.start, replace.end);
                } else {
                    for (StartAndEndHolder filter : filters) {
                        String beforeMatch = content.substring(0, content.indexOf(filter.start)).replaceAll(replace.start, replace.end);
                        String afterMatch = content.substring(content.lastIndexOf(filter.end) + 1).replaceAll(replace.start, replace.end);
                        String excludeString = content.substring(content.indexOf(filter.start), content.indexOf(filter.end) + 1);
                        content = beforeMatch + excludeString + afterMatch;
                    }
                }
                e.setValue(content);
            });
            AdapterSelector.write(dataMap, Paths.get(output));
        } catch (IOException e) {
            new MessageWindow(parent, "错误！", "错误，读取/输出文件失败：" + e.getMessage(), -1);
            return;
        } finally {
            processing.close();
        }
        new MessageWindow(parent, "完成", "Done!", 1);
    }

    // 判断keyFilter数组中否含有有意义的元素
    public boolean isKeyFilterOn() {
        return !(keyFilter == null || (keyFilter.length == 1 && keyFilter[0].equals("")));
    }

    // 判断keyFilter数组中是否有字符串在字符串str中
    public boolean isKeyFilterInStr(String str) {
        // 未启用keyFilter表示不进行任何过滤，因此必须返回true
        if (!isKeyFilterOn())
            return true;
        else return Arrays.stream(keyFilter).anyMatch(filter -> filter.contains(str));
    }

    // 判断一个字符串中是否包含中文
    public boolean isContainChineseChar(String str) {
        return chinesePattern.matcher(str).find();
    }

    // 将字符串首字母变为大写
    public String toUpperInitial(String str) {
        return (str == null || str.isEmpty()) ? "" : str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    // 将字符串首字母变为小写
    public String toLowerInitial(String str) {
        return (str == null || str.isEmpty()) ? "" : str.substring(0, 1).toLowerCase() + str.substring(1);
    }

    // 判断字符串是否在字符串数组filter中
    public Boolean isInFilter(String str) {
        if (filter == null)
            return false;
        else return Arrays.stream(filter).anyMatch(filter -> filter.toLowerCase().equals(str));
    }

    // 从List中删除全部指定字符串
    public void deleteElementFromList(String str, List<String> list) {
        list.removeIf(s -> s.equals(str));
    }

    // LA的第二个功能，等距添加字符串
    public void functionAdd() {
        int lineNumber = 1;
        int iInterval;
        if ((iInterval = verifyNumber(sInterval)) == -1)
            return;
        boolean outputTempFlag = false;
        MessageWindow mw = new MessageWindow(parentFrame, "处理中", "", 0);
        try {
            String str = "";
            String str1 = "";
            fis = new FileInputStream(fileInput1);
            isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
            bufferedReader = new BufferedReader(isr);
            if (fileInput1.equals(fileOutput)) {
                file = new File(dirToTempDir(fileOutput));
                outputTempFlag = true;
            } else
                file = new File(fileOutput);
            if (!file.exists()) {
                file.createNewFile();
            }
            fos = new FileOutputStream(file);
            outputStreamWriter = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
            // 用'='初始化，因为.lang文件中每行应该不会出现超过一个'='
            char specialBegin = '=';
            char specialEnd = '=';
            boolean ignoreFlag = true;
            // 由于目前只支持使用一对替换忽略符，因此该数组的长度非2即1(即输入为空情形)
            if (specialIgnoreSymbol.length == 2) {
                specialBegin = specialIgnoreSymbol[0].charAt(0);
                specialEnd = specialIgnoreSymbol[1].charAt(0);
            } else
                ignoreFlag = false;
            int lastCharCountDown = iInterval;
            int indexBegin = -1;
            int indexEnd = -1;
            while ((str = bufferedReader.readLine()) != null) {
                if (str.length() != 0 && str.charAt(0) != '#' && verifyLineNumber(lineNumber) >= 0
                        && str.indexOf('=') != -1 && isKeyFilterInStr(str.substring(0, str.indexOf('=')))) {
                    // 开始操作
                    str1 = str.substring(str.indexOf('=') + 1);
                    StringBuilder str2 = new StringBuilder();
                    while (!str1.equals("")) {
                        if (str1.length() > lastCharCountDown) {
                            if (ignoreFlag) {
                                indexBegin = str1.indexOf(specialBegin);
                                if (indexBegin == -1 || indexBegin + 1 > lastCharCountDown) {
                                    str2.append(str1, 0, lastCharCountDown).append(sAdd);
                                    str1 = str1.substring(lastCharCountDown);
                                    lastCharCountDown = iInterval;
                                } else {
                                    indexEnd = str1.indexOf(specialEnd);
                                    if (indexEnd != -1) {
                                        str2.append(str1, 0, indexEnd + 1);
                                        lastCharCountDown -= indexBegin;
                                        str1 = str1.substring(indexEnd + 1);
                                    } else {
                                        str2.append(str1, 0, lastCharCountDown).append(sAdd);
                                        str1 = str1.substring(lastCharCountDown);
                                        lastCharCountDown = iInterval;
                                    }
                                }
                            } else {
                                str2.append(str1, 0, lastCharCountDown).append(sAdd);
                                str1 = str1.substring(lastCharCountDown);
                            }
                        } else if (str1.length() <= lastCharCountDown) {
                            str2.append(str1);
                            str1 = "";
                        }
                    }
                    outputStreamWriter.write(str.substring(0, str.indexOf('=') + 1) + str2);
                    outputStreamWriter.write("\n");
                } else {
                    outputStreamWriter.write(str);
                    outputStreamWriter.write("\n");
                }
                lineNumber++;
            }
        } catch (FileNotFoundException e) {
            new MessageWindow(parentFrame, "错误！", "错误，找不到指定文件！", -1);
            return;
        } catch (IOException e) {
            new MessageWindow(parentFrame, "错误！", "错误，读取文件失败", -1);
            return;
        } finally {
            try {
                bufferedReader.close();
                isr.close();
                fis.close();
                outputStreamWriter.close();
                fos.close();
                mw.close();
                if (outputTempFlag) {
                    fileDelete(fileOutput);
                    file.renameTo(new File(fileOutput));
                }
            } catch (IOException e) {
                new MessageWindow(parentFrame, "错误！", "错误，关闭数据流失败！", -1);
                return;
            }
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            new MessageWindow(parentFrame, "错误！", "错误，未知错误！", -1);
            return;
        }
        new MessageWindow(parentFrame, "完成", "Done!", 1);
    }

    // LA的第三个功能，自动更新本地化文本
    public void functionUpdateLocalization() {
        MessageWindow mw = new MessageWindow(parentFrame, "处理中", "", 0);
        String tempOutput;
        if (!fileOutput.isEmpty()) {
            tempOutput = fileOutput.substring(0, fileOutput.lastIndexOf('.'))
                    + "TempLocalizationAssistantUpdateLocalization.txt";
        } else {
            return;
        }
        boolean informationModeStatus = (updateType % 10) == 1;
        boolean checkModeStatus = ((updateType / 10) % 10) == 1;
        updateType = updateType / 100;
        try {
            switch (updateType) {
                case 1:
                    functionUpdateLocalization(fileInput2, fileInput3, fileOutput);
                    break;
                case 2:
                    functionUpdateLocalization(fileInput1, fileInput2, fileInput3, fileOutput, false, checkModeStatus,
                            informationModeStatus);
                    break;
                case 3:
                    functionUpdateLocalization(fileInput2, fileInput3, tempOutput);
                    functionUpdateLocalization(fileInput1, fileInput2, tempOutput, fileOutput, false, checkModeStatus,
                            informationModeStatus);
                    fileDelete(tempOutput);
                    break;
                case 4:
                    functionUpdateLocalization(fileInput1, fileInput2, fileInput3, tempOutput, false, checkModeStatus,
                            informationModeStatus);
                    functionUpdateLocalization(fileInput2, tempOutput, fileOutput);
                    fileDelete(tempOutput);
                    break;
                case 5:
                    functionUpdateLocalization(fileInput1, fileInput2, fileInput3, fileOutput, true, checkModeStatus,
                            informationModeStatus);
                    break;
            }
        } catch (IOException e) {
            new MessageWindow(parentFrame, "错误！", "错误，读取/输出文件失败：" + e.getMessage(), -1);
            return;
        } finally {
            mw.close();
        }
        LogWindow lw;
        if (commentList.size() > 0) {
                lw = new LogWindow("更新完毕&警告信息");
                lw.label.setText("<html><body>更新完毕！但LA检测到旧版zh_CN.lang中有新版en_US.lang中没有的" + commentList.size()
                        + "处注释，<br/>这些内容未被更新，但可能是之前汉化者留下的提示信息，请手动校验它们。</body></html>");
                for (String comment : commentList)
                    lw.textArea.append(comment + "\n");
        } else {
            new MessageWindow(parentFrame, "完成", "Done!", 1);
        }
    }

    // 删除指定文件
    public void fileDelete(String location) {
        File file = new File(location);
        if (file.exists())
            file.delete();
    }

    // 功能三重载方法1
    public void functionUpdateLocalization(String oldzhFile, String enFile, String fileOutput) throws IOException {
        LangDataMap<String, String> enFileData = AdapterSelector.read(Paths.get(enFile));
        LangDataMap<String, String> oldzhFileData = AdapterSelector.read(Paths.get(oldzhFile));
        oldzhFileData.forEach((k, v) -> enFileData.computeIfPresent(k, (key, oldValue) -> v));
        AdapterSelector.write(enFileData, Paths.get(fileOutput));
    }

    // 功能三重载方法2
    public void functionUpdateLocalization(String oldenFile, String oldzhFile, String newenFile, String fileOutput,
                                           boolean doubleCheckFlag, boolean checkModeStatus, boolean informationModeStatus) throws IOException {
        //读取数据
        LangDataMap<String, String> oldenData = AdapterSelector.read(Paths.get(oldenFile));
        LangDataMap<String, String> oldzhData = AdapterSelector.read(Paths.get(oldzhFile));
        LangDataMap<String, String> newenData = AdapterSelector.read(Paths.get(newenFile));
        LangDataMap<String, String> enTozhData = new LangDataMap<>();
        //生成旧英文值-旧中文值数据
        oldzhData.forEach((k, v) -> {
            if (oldenData.containsKey(k)) {
                enTozhData.put(oldenData.get(k), v);
            }
        });
        //处理
        newenData.forEach((k, v) -> {
            if (enTozhData.containsKey(v)) {
                //doubleCheck：在匹配到新旧en相同文本时检查新旧key是否一致
                if (doubleCheckFlag && !oldenData.get(k).equalsIgnoreCase(v)) {
                    //TODO:实现信息模式和校验模式
                    return;
                }
                newenData.replace(k, enTozhData.get(v));
            }
        });
        //输出
        AdapterSelector.write(newenData, Paths.get(fileOutput));
        /** // hashtable:旧版英文值-旧版中文值，这里认为旧版英文键内部或中文键内部不会出现大小写不同的键
         Set<String> keys1 = oldenTable.keySet();
        for (String key : keys1)
         if (oldzhTable.containsKey(key))
         if (checkModeStatus == 1 && enTozhTable.containsKey(oldenTable.get(key))
         && !oldzhTable.get(key).equals(enTozhTable.get(oldenTable.get(key))))
         enTozhTable.put(oldenTable.get(key), "##Warning:" + oldzhTable.get(key));
                else
         enTozhTable.put(oldenTable.get(key), oldzhTable.get(key));
        boolean outputTempFlag = false;
        try {
            fis = new FileInputStream(fileInput3);
         isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
         bufferedReader = new BufferedReader(isr);
            if (fileInput3.equals(fileOutput)) {
                file = new File(dirToTempDir(fileOutput));
                outputTempFlag = true;
            } else
                file = new File(fileOutput);
            if (!file.exists()) {
                file.createNewFile();
            }
            fos = new FileOutputStream(file);
         outputStreamWriter = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
         while ((input = bufferedReader.readLine()) != null) {
         if (input.length() != 0 && !input.startsWith("#") && !input.startsWith("//") && input.indexOf('=') != -1) {
                    if (doubleCheckFlag == 0) {
         String inputKey = input.substring(0, input.indexOf('=')); // key
         String inputValue = input.substring(input.indexOf('=') + 1); // value
         // 若未发现新版某行与旧版有相同的英文值，则str3仍未该行；
         // 若发现与旧版有相同的值，则将=后替换为旧版中文值；
         // 特别地，若开启校验模式，则当该英文值对应多个不同旧版中文值时，还要在str3前面加上##Warning:
         if (enTozhTable.containsKey(inputValue.toLowerCase()))
         if (!enTozhTable.get(inputValue.toLowerCase()).contains("##Warning:"))
         output = input.substring(0, input.indexOf('=') + 1) + enTozhTable.get(inputValue.toLowerCase());
                            else
         output = "##Warning:" + input.substring(0, input.indexOf('=') + 1)
         + enTozhTable.get(inputValue.toLowerCase()).replaceAll("##Warning:", "");
                        else
         output = input;
         // 当勾选显示变更信息后，还要对output进一步处理才能输出到文件中
         // output和input忽略大小写相等意味着该行英文值在旧版中不存在.此时需要显示变更信息
         if (informationModeStatus == 1 && output.toLowerCase().equals(input.toLowerCase()))
                            // 之后再判断该行英文键是否在旧版中存在，若存在，则说明该行文本更新了
         if (oldzhTable.containsKey(inputKey.toLowerCase())
         && !oldzhTable.get(inputKey.toLowerCase()).toLowerCase().equals(inputValue.toLowerCase())) {
         outputStreamWriter.write("##-:" + inputKey + "=" + oldenTable.get(inputKey.toLowerCase()));
         outputStreamWriter.write("\n");
         outputStreamWriter.write("##+:" + output);
         outputStreamWriter.write("\n");
         outputStreamWriter.write("##Replace:" + inputKey + "=" + oldzhTable.get(inputKey.toLowerCase()));
         outputStreamWriter.write("\n");
                            } else {
         outputStreamWriter.write("##New:" + input);
         outputStreamWriter.write("\n");
                            }
                        else {
         outputStreamWriter.write(output);
         outputStreamWriter.write("\n");
                        }
                    } else if (doubleCheckFlag == 1) {
                        if (informationModeStatus == 0) {
         inputValue1 = input.substring(input.indexOf('=') + 1);
         inputKey1 = input.substring(0, input.indexOf('='));
         //doubleCheck：
         //在匹配到新旧en相同文本时检查新旧key是否一致
         if (enTozhTable.containsKey(inputValue1.toLowerCase())
         && oldenTable.containsKey(inputKey1.toLowerCase())
         && oldenTable.get(inputKey1.toLowerCase()).toLowerCase().equals(inputValue1.toLowerCase()))
         output = inputKey1 + "=" + enTozhTable.get(inputValue1.toLowerCase());
                            else
         output = input;
         outputStreamWriter.write(output);
         outputStreamWriter.write("\n");
                        } else {
         inputValue1 = input.substring(input.indexOf('=') + 1);
         inputKey1 = input.substring(0, input.indexOf('='));
         if (enTozhTable.containsKey(inputValue1.toLowerCase()) && oldenTable.containsKey(inputKey1.toLowerCase())
         && oldenTable.get(inputKey1.toLowerCase()).toLowerCase().equals(inputValue1.toLowerCase())) {
         inputValue1 = inputKey1 + "=" + enTozhTable.get(inputValue1.toLowerCase());
         outputStreamWriter.write(inputValue1);
         outputStreamWriter.write("\n");
         } else if (enTozhTable.containsKey(inputValue1.toLowerCase())
         && oldenTable.containsKey(inputKey1.toLowerCase())
         && !oldenTable.get(inputKey1.toLowerCase()).toLowerCase().equals(inputValue1.toLowerCase())) {
         outputStreamWriter.write("##-:" + inputKey1 + "=" + oldenTable.get(inputKey1.toLowerCase()));
         outputStreamWriter.write("\n");
         outputStreamWriter.write("##+:" + input);
         outputStreamWriter.write("\n");
         outputStreamWriter.write("##Replace:" + inputKey1 + "=" + enTozhTable.get(inputValue1.toLowerCase()));
         outputStreamWriter.write("\n");
                            } else {
         outputStreamWriter.write("##New:" + input);
         outputStreamWriter.write("\n");
                            }
                        }
                    }
                } else {
         //删除注释
         if ((updateType == 2 || updateType == 5) && input.length() != 0
         && (input.startsWith("#") || input.startsWith("//")))
         deleteElementFromList(input, commentList);
         outputStreamWriter.write(input);
         outputStreamWriter.write("\n");
                }
            }
        } catch (FileNotFoundException e) {
            mw.close();
            new MessageWindow(parentFrame, "错误！", "错误，找不到指定文件！", -1);
         return false;
        } catch (IOException e) {
            mw.close();
            new MessageWindow(parentFrame, "错误！", "错误，读取文件失败", -1);
         return false;
        } finally {
            mw.close();
            try {
         bufferedReader.close();
                isr.close();
                fis.close();
         outputStreamWriter.close();
                fos.close();
                if (outputTempFlag) {
                    fileDelete(fileOutput);
                    file.renameTo(new File(fileOutput));
                }
            } catch (Exception e) {
                new MessageWindow(parentFrame, "错误！", "错误，关闭数据流失败！", -1);
         return false;
            }
         }*/

    }

    // LA的第四个功能，逐行条件清理
    public int functionConditionalRemove() {
        MessageWindow mw = new MessageWindow(parentFrame, "处理中", "", 0);
        int lineNumber = 1;
        int updateType1 = updateType / 10;
        int updateType2 = updateType % 10;
        boolean outputTempFlag = false;
        try {
            String str = "";
            String str1 = "";
            fis = new FileInputStream(fileInput1);
            isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
            bufferedReader = new BufferedReader(isr);
            if (fileInput1.equals(fileOutput)) {
                file = new File(dirToTempDir(fileOutput));
                outputTempFlag = true;
            } else
                file = new File(fileOutput);
            if (!file.exists()) {
                file.createNewFile();
            }
            fos = new FileOutputStream(file);
            outputStreamWriter = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
            while ((str = bufferedReader.readLine()) != null) {
            	boolean lineFeedFlag = true;
                if (verifyLineNumber(lineNumber) >= 0) {
                    if (sRemoveFlag.isEmpty()) {
                    	str1 = "";
                    	if (updateType == 0)
                    		lineFeedFlag = false;
                    } else if (!str.startsWith("//") && (!str.startsWith("#") || str.startsWith("##"))) {
                        if (updateType2 == 1) {
                            Pattern pattern = Pattern.compile("##[a-zA-Z]+:");
                            Matcher matcher = pattern.matcher(str);
                            if (matcher.find())
                                str = str.substring(matcher.end());
                        }
                        if (updateType1 == 0) {
                            if (str.length() != 0 && str.contains(sRemoveFlag))
                                str1 = str.substring(0, str.indexOf(sRemoveFlag));
                            else
                                str1 = str;
                        } else {
                            if (str.length() != 0 && str.contains(sRemoveFlag)
                                && (str.indexOf(sRemoveFlag) + sRemoveFlag.length() < str.length()))
                        		str1 = str.substring(0, str.indexOf(sRemoveFlag) + sRemoveFlag.length());
                        	else
                        		str1 = str;
                        }
                    } else
                    	str1 = str;
                } else
                    str1 = str;
                outputStreamWriter.write(str1);
                if (lineFeedFlag) outputStreamWriter.write("\n");
                lineNumber++;
            }
        } catch (FileNotFoundException e) {
            new MessageWindow(parentFrame, "错误！", "错误，找不到指定文件！", -1);
            return -1;
        } catch (IOException e) {
            new MessageWindow(parentFrame, "错误！", "错误，读取文件失败", -1);
            return -1;
        } finally {
            try {
                bufferedReader.close();
                isr.close();
                fis.close();
                outputStreamWriter.close();
                fos.close();
                mw.close();
                if (outputTempFlag) {
                    fileDelete(fileOutput);
                    file.renameTo(new File(fileOutput));
                }
            } catch (IOException e) {
                new MessageWindow(parentFrame, "错误！", "错误，关闭数据流失败！", -1);
                return -1;
            }
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            new MessageWindow(parentFrame, "错误！", "错误，未知错误！", -1);
            return -1;
        }
        new MessageWindow(parentFrame, "完成", "Done!", 1);
        return 0;
    }

    // LA的第5个功能，已译文本替换
    public int functionAutoReplaceEnglishTextWithChineseTranslation() {
        Hashtable<String, String> hashtable1 = new Hashtable<>();
        Hashtable<String, String> hashtable2 = new Hashtable<>();
        Hashtable<String, String> hashtable3 = new Hashtable<>();
        int updateType = this.updateType / 10;
        int checkModeStatus = this.updateType % 10;
        String str = "";
        String str1 = "";
        String str2 = "";
        String str3 = ""; // output String
        MessageWindow mw = new MessageWindow(parentFrame, "处理中", "", 0);
        // 载入第一个文件
        try {
            fis = new FileInputStream(fileInput1);
            isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
            bufferedReader = new BufferedReader(isr);
            while ((str = bufferedReader.readLine()) != null) {
                if (str.length() != 0 && str.charAt(0) != '#' && str.indexOf('=') != -1) {
                    if (updateType == 0) {
                        // hashtable1:英文文本小写键-小写英文值
                        str1 = str.substring(0, str.indexOf('=')).toLowerCase();
                        str2 = str.substring(str.indexOf('=') + 1);
                        hashtable1.put(str1, str2.toLowerCase());
                    } else if (updateType == 1) {
                        // hashtable3:英文值-中文值
                        str1 = str.substring(0, str.indexOf('='));
                        str2 = str.substring(str.indexOf('=') + 1);
                        if (hashtable3.containsKey(str1) && !hashtable3.get(str1).equals(str2))
                            hashtable3.put(str1, "##Warnging:" + str2);
                        else
                            hashtable3.put(str1, str2);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            mw.close();
            new MessageWindow(parentFrame, "错误！", "错误，找不到指定文件！", -1);
            return -1;
        } catch (IOException e) {
            new MessageWindow(parentFrame, "错误！", "错误，读取文件失败", -1);
            return -1;
        } finally {
            try {
                bufferedReader.close();
                isr.close();
                fis.close();
            } catch (Exception e) {
                mw.close();
                new MessageWindow(parentFrame, "错误！", "错误，关闭数据流失败！", -1);
                return -1;
            }
        }
        // 当以执行默认模式时，载入第二个文件并创建hashtable3
        if (updateType == 0) {
            try {
                fis = new FileInputStream(fileInput2);
                isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
                bufferedReader = new BufferedReader(isr);
                while ((str = bufferedReader.readLine()) != null) {
                    if (str.length() != 0 && str.charAt(0) != '#' && str.indexOf('=') != -1
                            && str.indexOf('=') != str.length() - 1) {
                        // hashtable2:中文文本小写键-值(这儿需要判断它里面是否包含汉字,不包含汉字的值略过)
                        str1 = str.substring(0, str.indexOf('=')).toLowerCase();
                        str2 = str.substring(str.indexOf('=') + 1);
                        if (isContainChineseChar(str2))
                            hashtable2.put(str1, str2);
                    }
                }
            } catch (FileNotFoundException e) {
                mw.close();
                new MessageWindow(parentFrame, "错误！", "错误，找不到指定文件！", -1);
                return -1;
            } catch (IOException e) {
                new MessageWindow(parentFrame, "错误！", "错误，读取文件失败", -1);
                return -1;
            } finally {
                try {
                    bufferedReader.close();
                    isr.close();
                    fis.close();
                } catch (Exception e) {
                    mw.close();
                    new MessageWindow(parentFrame, "错误！", "错误，关闭数据流失败！", -1);
                    return -1;
                }
            }
            // hashtable3:已译英文值-中文值，这里认为英文键内部或中文键内部不会出现大小写不同的键
            Set<String> keys1 = hashtable1.keySet();
            for (String key : keys1)
                if (hashtable2.containsKey(key))
                    if (checkModeStatus == 1 && hashtable3.containsKey(hashtable1.get(key))
                            && !hashtable2.get(key).equals(hashtable3.get(hashtable1.get(key))))
                        hashtable3.put(hashtable1.get(key), "##Warning:" + hashtable2.get(key));
                    else
                        hashtable3.put(hashtable1.get(key), hashtable2.get(key));
        }
        // 读取文件3，进行操作并输入到文件4
        boolean outputTempFlag = false;
        try {
            fis = new FileInputStream(fileInput3);
            isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
            bufferedReader = new BufferedReader(isr);
            if (fileInput3.equals(fileOutput)) {
                file = new File(dirToTempDir(fileOutput));
                outputTempFlag = true;
            } else
                file = new File(fileOutput);
            if (!file.exists()) {
                file.createNewFile();
            }
            fos = new FileOutputStream(file);
            outputStreamWriter = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
            while ((str = bufferedReader.readLine()) != null) {
                if (str.length() != 0 && str.charAt(0) != '#' && str.indexOf('=') != -1) {
                    str1 = str.substring(0, str.indexOf('='));
                    str2 = str.substring(str.indexOf('=') + 1);
                    int warningFlag = 0;
                    String strTemp;
                    for (String key : hashtable3.keySet()) {
                        str3 = toLowerInitial(key);
                        if (str2.contains(str3)) {
                            strTemp = hashtable3.get(key);
                            if (!strTemp.contains("##Warning:"))
                                str2 = str2.replaceAll(str3, strTemp);
                            else {
                                warningFlag = 1;
                                str2 = str2.replaceAll(str3, strTemp.replace("##Warning:", ""));
                            }
                        }
                        str3 = toUpperInitial(key);
                        if (str2.contains(str3)) {
                            strTemp = hashtable3.get(key);
                            if (!strTemp.contains("##Warning:"))
                                str2 = str2.replaceAll(str3, strTemp);
                            else {
                                warningFlag = 1;
                                str2 = str2.replaceAll(str3, strTemp.replace("##Warning:", ""));
                            }
                        }
                    }
                    str = str1 + "=" + str2;
                    str = warningFlag == 0 ? str : "##Warnging:" + str;
                }
                outputStreamWriter.write(str);
                outputStreamWriter.write("\n");
            }
        } catch (FileNotFoundException e) {
            mw.close();
            new MessageWindow(parentFrame, "错误！", "错误，找不到指定文件！", -1);
            return -1;
        } catch (IOException e) {
            mw.close();
            new MessageWindow(parentFrame, "错误！", "错误，读取文件失败", -1);
            return -1;
        } finally {
            try {
                bufferedReader.close();
                isr.close();
                fis.close();
                outputStreamWriter.close();
                fos.close();
                mw.close();
                if (outputTempFlag) {
                    fileDelete(fileOutput);
                    file.renameTo(new File(fileOutput));
                }
            } catch (Exception e) {
                mw.close();
                new MessageWindow(parentFrame, "错误！", "错误，关闭数据流失败！", -1);
                return -1;
            }
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            mw.close();
            new MessageWindow(parentFrame, "错误！", "错误，未知错误！", -1);
            return -1;
        }
        new MessageWindow(parentFrame, "完成", "Done!", 1);
        return 0;
    }

    // LA的第6个功能，统计高频词汇
    public int functionWordCount() {
        // 读取文件，进行操作并输入到文件
        Map<String, Integer> map = new HashMap<>();
        String str = "";
        String str1 = "";
        boolean outputTempFlag = false;
        MessageWindow mw = new MessageWindow(parentFrame, "处理中", "", 0);
        try {
            fis = new FileInputStream(fileInput1);
            isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
            bufferedReader = new BufferedReader(isr);
            if (fileInput1.equals(fileOutput)) {
                file = new File(dirToTempDir(fileOutput));
                outputTempFlag = true;
            } else
                file = new File(fileOutput);
            if (!file.exists()) {
                file.createNewFile();
            }
            fos = new FileOutputStream(file);
            outputStreamWriter = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
            while ((str = bufferedReader.readLine()) != null) {
                if (str.length() != 0 && str.charAt(0) != '#' && str.indexOf('=') != -1) {
                    String[] sArrayTemp;
                    str1 = str.substring(str.indexOf('=') + 1);
                    sArrayTemp = str1.split(" ");
                    for (String s : sArrayTemp) {
                        String sTemp = s.toLowerCase();
                        sTemp = sTemp.replaceAll("\\.", "");
                        sTemp = sTemp.replaceAll(",", "");
                        sTemp = sTemp.replaceAll("\"", "");
                        if (isInFilter(sTemp))
                            continue;
                        if (map.containsKey(sTemp))
                            map.put(sTemp, map.get(sTemp) + 1);
                        else
                            map.put(sTemp, 1);
                    }
                }
            }
            List<Map.Entry<String, Integer>> list = new ArrayList<>(map.entrySet());
            list.sort((o1, o2) -> (o2.getValue() - o1.getValue()));
            for (Map.Entry<String, Integer> stringIntegerEntry : list) {
                outputStreamWriter.write(stringIntegerEntry.toString());
                outputStreamWriter.write("\n");
            }
        } catch (FileNotFoundException e) {
            new MessageWindow(parentFrame, "错误！", "错误，找不到指定文件！", -1);
            return -1;
        } catch (IOException e) {
            new MessageWindow(parentFrame, "错误！", "错误，读取文件失败", -1);
            return -1;
        } finally {
            try {
                bufferedReader.close();
                isr.close();
                fis.close();
                outputStreamWriter.close();
                fos.close();
                mw.close();
                if (outputTempFlag) {
                    fileDelete(fileOutput);
                    file.renameTo(new File(fileOutput));
                }
            } catch (IOException e) {
                new MessageWindow(parentFrame, "错误！", "错误，关闭数据流失败！", -1);
                return -1;
            }
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            new MessageWindow(parentFrame, "错误！", "错误，未知错误！", -1);
            return -1;
        }
        new MessageWindow(parentFrame, "完成", "Done!", 1);
        return 0;
    }

    static class StartAndEndHolder {
        public String start;
        public String end;

        StartAndEndHolder(String start, String end) {
            this.start = start;
            this.end = end;
        }
    }

}
