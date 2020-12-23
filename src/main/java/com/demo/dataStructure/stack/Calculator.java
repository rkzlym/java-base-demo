package com.demo.dataStructure.stack;

/**
 * 使用栈实现计算器
 * 数栈：numStack
 * 符号栈：operStack
 *
 * 遍历表达式
 * 如果是数字则入数栈
 * 如果是符号则入符号栈
 *  如果符号栈有操作符且当前的操作符的优先级<=栈中的操作符，就需要从数栈中pop出两个数，再从符号栈中pop出一个符号，进行运算，将得到的结果入数栈，
 *  然后将当前的操作符入符号栈，如果当前操作符优先级>栈中的操作符就直接入栈，当表达式扫描完毕，就顺序的从数栈和符号栈中pop出相应的数和符号并运算
 *  最后数栈只有一个数字 即结果
 */
public class Calculator {
}