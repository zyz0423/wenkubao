package com.wenkubao.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtil {
 public static final String MULTI_WORD_CN = "([\\u4e00-\\u9fa5\\w]+,*)+";
 private static final Logger LOGGER = LoggerFactory.getLogger(RegexUtil.class);

 private RegexUtil() {
     throw new IllegalAccessError("Utility class");
 }

 public static boolean isMatch(String regex, String input) {
     Matcher matcher = Pattern.compile(regex).matcher(input);
     return matcher.find();
 }

 public static String getValue(String regex, String text) {
     return getValue(regex, text, 0);
 }

 public static String getValue(String regex, String text, int group) {
     try {
         Matcher matcher = Pattern.compile(regex).matcher(text);
         return matcher.find() ? matcher.group(group) : "";
     } catch (Exception var4) {
         LOGGER.error(var4.getMessage(), var4);
         return "";
     }
 }

 public static String[] getValue(String regex, String text, int... group) {
     String[] val = new String[group.length];

     try {
         Matcher matcher = Pattern.compile(regex).matcher(text);
         if (matcher.find()) {
             for(int i = 0; i < group.length; ++i) {
                 val[i] = matcher.group(group[i]);
             }
         }
     } catch (Exception var6) {
         LOGGER.error(var6.getMessage(), var6);
     }

     return val;
 }

 public static List<String> getMatches(String regex, String text) {
     return getMatches(regex, text, 1);
 }

 public static List<String> getMatches(String regex, String text, int group) {
     List<String> matches = new ArrayList();
     Matcher matcher = Pattern.compile(regex).matcher(text);

     while(matcher.find()) {
         matches.add(matcher.group(group));
     }

     return matches;
 }
}
