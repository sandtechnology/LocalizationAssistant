package pers.gwyog.localizationassistant.utils;

import javax.swing.*;
import java.awt.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedHashSet;
import java.util.Set;

public class ContainerUtils {
    private static Set<Component> addedComponents = new LinkedHashSet<>();

    private ContainerUtils() {
    }

    /**
     * Bypass add operation
     *
     * @param container
     * @param component
     */
    public static void addComponent(Container container, Component component) {
        if (!addedComponents.contains(component)) {
            container.add(component);
            addedComponents.add(component);
            try {
                System.out.print("L" + Thread.currentThread().getStackTrace()[2].getLineNumber());
                System.out.println("：Label " + component.getClass().getMethod("getText").invoke(component) + "->" + container.getClass().getName());
            } catch (Exception e) {
                System.out.println("：" + component.getClass().getName() + "->" + container.getClass().getName());
            }
        } else {
            if (component instanceof JLabel) {
                container.add(new JLabel(((JLabel) component).getText()));
                System.out.println("Cloning...." + ((JLabel) component).getText());
            }
        }
    }

    /***
     *
     * 自动填充输出文件名
     *
     * @param from 输入文件的JTextField
     * @param to 输出文件的JTextField
     */
    public static void autoFill(JTextField from, JTextField to) {
        PathProcessor pathProcessor = new PathProcessor(from.getText());
        //检查是否有扩展名
        if (pathProcessor.isExtNotExist()) {
            return;
        }
        //开始寻找可用的文件名
        for (int i = 1; Files.exists(Paths.get(pathProcessor.getFullPath())); i++) {
            pathProcessor.setSuffix("-Output" + i);
        }
        //将可用文件名填充到输出文件的JTextField
        to.setText(pathProcessor.getFullPath());
    }
}
