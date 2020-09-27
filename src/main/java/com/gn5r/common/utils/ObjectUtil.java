package com.gn5r.common.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.gn5r.common.resource.Difference;

import org.apache.commons.lang3.ObjectUtils;

/**
 * <p>
 * {@link ObjectUtils}拡張クラス
 * </p>
 *
 * <ul>
 * <li><b>diffSameObject</b> - 同一クラスオブジェクトのフィールドパラメータを比較する。パラメータに相違があれば
 * {@link Difference} のリストを返却する。相違がなければ空のリストを返却する。</li>
 * <li><b>checkSameObject</b> - 同一クラスオブジェクトのフィールドパラメータを比較する。パラメータに相違があれば true
 * を、相違がなければ false を返却する。</li>
 * <li><b>diff</b> - クラスオブジェクトのフィールドパラメータを比較する。パラメータに相違があれば
 * {@link Difference} のリストを返却する。相違がなければ空のリストを返却する</li>
 * <li><b>check</b> - クラスオブジェクトのフィールドパラメータを比較する。パラメータに相違があれば true を、相違がなければ
 * false を返却する</li>
 * </ul>
 *
 * @author gn5r
 * @since 0.1.2-RELEASE
 * @see ObjectUtils
 */
public final class ObjectUtil extends ObjectUtils {

    /**
     * 同一クラスオブジェクトのフィールドパラメータを比較する。パラメータに相違があれば
     * {@link Difference} のリストを返却する。相違がなければ空のリストを返却する
     * 
     * @param <T>      比較するオブジェクトのタイプ
     * @param a        オブジェクトa
     * @param b        オブジェクトb
     * @param excludes 除外フィールド名のString配列
     * @return {@link Difference} 相違フィールドリスト
     * @throws NullPointerException     オブジェクトaまたはオブジェクトbが {@code null} の場合にthrowする
     * @throws IllegalArgumentException オブジェクトaまたはオブジェクトbのフィールドにアクセスできなかった場合にthrowする
     */
    public static final <T> List<Difference> diffSameObject(T a, T b, String... excludes) {
        List<Difference> diffList = new ArrayList<Difference>();

        // チェック処理
        checkObjectNull(a, b);
        checkSameClassName(a, b);

        for (Field field : a.getClass().getDeclaredFields()) {

            // privateフィールドにアクセスするには #setAccessible をtrueにする必要がある
            field.setAccessible(true);
            final String fieldName = field.getName();

            // 除外フィールドは比較しない
            if (!Objects.isNull(excludes) && Arrays.asList(excludes).contains(fieldName))
                continue;

            try {
                final Object paramA = field.get(a);
                final Object paramB = field.get(b);

                if (!Objects.equals(paramA, paramB)) {
                    final Difference e = new Difference(fieldName, paramA, paramB);
                    diffList.add(e);
                }
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return diffList;
    }

    /**
     * 同一クラスオブジェクトのフィールドパラメータを比較する。パラメータに相違があれば true を、相違がなければ false を返却する。
     * 
     * @param <T>      比較するオブジェクトタイプ
     * @param a        オブジェクトa
     * @param b        オブジェクトb
     * @param excludes 除外フィールド名のString配列
     * @return 相違有無
     * @throws NullPointerException     オブジェクトaまたはオブジェクトbが {@code null} の場合にthrowする
     * @throws IllegalArgumentException オブジェクトaまたはオブジェクトbのフィールドにアクセスできなかった場合にthrowする
     */
    public static final <T> boolean checkSameObject(T a, T b, String... excludes) {
        // チェック処理
        checkObjectNull(a, b);
        checkObjectNull(a, b);
        checkSameClassName(a, b);

        // privateフィールドにアクセスするには #getDeclaredFields を使う必要がある
        for (Field field : a.getClass().getDeclaredFields()) {
            final String fieldName = field.getName();

            // 除外フィールドは比較しない
            if (!Objects.isNull(excludes) && Arrays.asList(excludes).contains(fieldName))
                continue;

            try {
                final Object paramA = field.get(a);
                final Object paramB = field.get(b);

                if (Objects.equals(paramA, paramB)) {
                    return true;
                }
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    /**
     * クラスオブジェクトの同一フィールドパラメータを比較する。パラメータに相違があれば
     * {@link Difference}のリストを返却する。相違がなければ空のリストを返却する
     * <p>
     * 同一フィールドが存在しない場合はパラメータの比較をしないので空のリストが返却される
     * </p>
     * 
     * @param <T>      比較するオブジェクトタイプ
     * @param a        オブジェクトa
     * @param b        オブジェクトb
     * @param excludes 除外フィールド名のString配列
     * @return {@link Difference} 相違フィールドリスト
     * @throws NullPointerException     オブジェクトaまたはオブジェクトbが {@code null} の場合にthrowする
     * @throws NoSuchFieldException     オブジェクトaまたはオブジェクトbのフィールドが見つからない場合にthrowする
     * @throws IllegalArgumentException オブジェクトaまたはオブジェクトbのフィールドにアクセスできなかった場合にthrowする
     */
    public static final <T> List<Difference> diff(T a, T b, String... excludes) {
        List<Difference> diffList = new ArrayList<>();

        // チェック処理
        checkObjectNull(a, b);

        // 同一フィールドリスト
        List<String> sameField = new ArrayList<>();

        // オブジェクトaのフィールド一覧を取得
        List<String> fieldAList = getFieldNames(a, excludes);

        // オブジェクトbのフィールド一覧を取得
        List<String> fieldBList = getFieldNames(b, excludes);

        // オブジェクトaとオブジェクトbの同一フィールドを取得する
        sameField = fieldAList.stream().filter(name -> fieldBList.contains(name)).collect(Collectors.toList());

        for (String name : sameField) {
            try {
                Field fieldA = a.getClass().getDeclaredField(name);
                fieldA.setAccessible(true);
                Field fieldB = b.getClass().getDeclaredField(name);
                fieldB.setAccessible(true);

                try {
                    final Object paramA = fieldA.get(a);
                    final Object paramB = fieldB.get(b);

                    if (!Objects.equals(paramA, paramB)) {
                        final Difference e = new Difference(name, paramA, paramB);
                        diffList.add(e);
                    }
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    e.printStackTrace();
                }

            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }

        return diffList;
    }

    /**
     * クラスオブジェクトのフィールドパラメータを比較する。パラメータに相違があれば {@value true} を、相違がなければ {@value false}
     * を返却する。
     * 
     * @param <T>      比較するオブジェクトタイプ
     * @param a        オブジェクトa
     * @param b        オブジェクトb
     * @param excludes 除外フィールド名のString配列
     * @return 相違有無
     * @throws NullPointerException     オブジェクトaまたはオブジェクトbが {@code null} の場合にthrowする
     * @throws NoSuchFieldException     オブジェクトaまたはオブジェクトbのフィールドが見つからない場合にthrowする
     * @throws IllegalArgumentException オブジェクトaまたはオブジェクトbのフィールドにアクセスできなかった場合にthrowする
     */
    public static final <T> boolean check(T a, T b, String... excludes) {
        // チェック処理
        checkObjectNull(a, b);

        // 同一フィールドリスト
        List<String> sameField = new ArrayList<>();

        // オブジェクトaのフィールド一覧を取得
        List<String> fieldAList = getFieldNames(a, excludes);

        // オブジェクトbのフィールド一覧を取得
        List<String> fieldBList = getFieldNames(b, excludes);

        // オブジェクトaとオブジェクトbの同一フィールドを取得する
        sameField = fieldAList.stream().filter(name -> fieldBList.contains(name)).collect(Collectors.toList());

        for (String name : sameField) {
            try {
                Field fieldA = a.getClass().getDeclaredField(name);
                fieldA.setAccessible(true);
                Field fieldB = b.getClass().getDeclaredField(name);
                fieldB.setAccessible(true);

                try {
                    final Object paramA = fieldA.get(a);
                    final Object paramB = fieldB.get(b);

                    // フィールドパラメータが一致すればtrueを返却する
                    if (Objects.equals(paramA, paramB)) {
                        return true;
                    }
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    e.printStackTrace();
                }

            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    /**
     * オブジェクトがnullかどうかをチェックし、nullであるならば {@link NullPointerException} をthrowする
     * 
     * @param a オブジェクトa
     * @param b オブジェクトb
     * @throws NullPointerException オブジェクトaまたはオブジェクトbがNullの場合にthrowする
     */
    private static final void checkObjectNull(Object a, Object b) {
        if (Objects.isNull(a)) {
            throw new NullPointerException("オブジェクトaがnullです");
        }

        if (Objects.isNull(b)) {
            throw new NullPointerException("オブジェクトbがnullです");
        }
    }

    /**
     * オブジェクトのクラス名が同一かチェックする。同一でない場合は {@link IllegalArgumentException} をthrowする
     * 
     * @param a オブジェクトa
     * @param b オブジェクトb
     * @throws IllegalArgumentException オブジェクトa及びオブジェクトbのクラス名が同一でないときにthrowする
     */
    private static final void checkSameClassName(Object a, Object b) {
        final String classNameA = a.getClass().getName();
        final String classNameB = b.getClass().getName();

        if (!Objects.equals(classNameA, classNameB)) {
            throw new IllegalArgumentException("同一クラス以外では比較できません");
        }
    }

    /**
     * オブジェクトのフィールドリストを返却する
     * 
     * @param object   フィールド一覧を取得したいオブジェクト
     * @param excludes 除外フィールド名のString配列
     * @return フィールドリスト
     */
    public static final List<String> getFieldNames(Object object, String... excludes) {
        // 念のためthis$0を除外しておく
        return Arrays.asList(object.getClass().getDeclaredFields()).stream().map(Field::getName)
                .filter(name -> !Arrays.asList(excludes).contains(name) && !name.matches("this\\$0"))
                .collect(Collectors.toList());
    }
}