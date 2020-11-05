package com.gn5r.common.utils;

import java.lang.reflect.Array;
import java.util.Objects;

import org.apache.commons.lang3.ArrayUtils;

/**
 * {@link ArrayUtils}拡張クラス
 * 
 * <ul>
 * <li><b>push</b> - オブジェクト配列の末尾に1つ以上の要素を追加する</li>
 * </ul>
 * 
 * @author gn5r
 * @since 0.2.0
 * @see ArrayUtils
 */
public final class ArrayUtil extends ArrayUtils {

    /**
     * <p>
     * オブジェクト配列の末尾に1つ以上の要素を追加する
     * </p>
     * 
     * <p>
     * 配列または追加したい要素がnullと判定された場合は配列をそのまま返却する
     * </p>
     * 
     * @param <T>     配列のオブジェクトタイプ
     * @param array   オブジェクト配列
     * @param element 追加したい要素
     * @return 要素を追加した新しいオブジェクト配列
     * @since 0.2.0
     */
    @SuppressWarnings("unchecked")
    public static final <T> T[] push(T[] array, Object... element) {

        if (Objects.isNull(array)) {
            return null;
        }

        if (Objects.isNull(element)) {
            return array;
        }

        final int length = array.length;
        final int elemtLength = element.length;

        // temp配列の初期化
        T[] tmp = (T[]) Array.newInstance(array.getClass().getComponentType(), length + elemtLength);

        // temp配列に現在の配列をコピーする
        System.arraycopy(array, 0, tmp, 0, length);

        for (int i = 0; i < elemtLength; i++) {
            tmp[length + i] = (T) element[i];
        }

        return tmp;
    }

    /**
     * <p>
     * オブジェクト配列の先頭に1つ以上の要素を追加する
     * </p>
     * 
     * <p>
     * 配列または追加したい要素がnullと判定された場合は配列をそのまま返却する
     * </p>
     * 
     * @param <T>     配列のオブジェクトタイプ
     * 
     * @param array   オブジェクト配列
     * @param element 追加したい要素
     * @return 要素を追加した新しいオブジェクト配列
     * @since 0.2.0
     */
    @SuppressWarnings("unchecked")
    public static final <T> T[] unshift(T[] array, Object... element) {

        if (Objects.isNull(array)) {
            return null;
        }

        if (Objects.isNull(element)) {
            return array;
        }

        final int length = array.length;
        final int elemtLength = element.length;

        T[] tmp = (T[]) Array.newInstance(array.getClass().getComponentType(), length + elemtLength);

        System.arraycopy(element, 0, tmp, 0, elemtLength);

        for (int i = 0; i < length; i++) {
            tmp[length + i] = array[i];
        }

        return tmp;
    }

    /**
     * <p>
     * オブジェクト配列から指定した要素の位置を返却する。見つからない場合は -1 を返却する
     * </p>
     * 
     * @param <T>     配列のオブジェクトタイプ
     * @param array   オブジェクト配列
     * @param element 位置を取得したい要素
     * @return 要素の位置
     * @since 0.3.4
     */
    public static final <T> int findIndex(T[] array, Object element) {
        if (Objects.isNull(array)) {
            return -1;
        }

        if (Objects.isNull(element)) {
            return -1;
        }

        for (int i = 0; i < array.length; i++) {
            if (Objects.equals(array[i], element)) {
                return i;
            }
        }

        return -1;
    }
}