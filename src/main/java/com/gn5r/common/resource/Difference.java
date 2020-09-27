package com.gn5r.common.resource;

/**
 * オブジェクト比較リソースクラス
 * 
 * @author gn5r
 * @since 0.1.2-RELEASE
 */
public final class Difference {

    private String fieldName;
    private Object a;
    private Object b;

    /**
     * 引数なしのコンストラクタ
     */
    public Difference() {

    }

    /**
     * 引数ありのコンストラクタ
     * 
     * @param fieldName フィールド名(変数名)
     * @param a         オブジェクトaのフィールドパラメータ
     * @param b         オブジェクトbのフィールドパラメータ
     */
    public Difference(final String fieldName, final Object a, final Object b) {
        this.fieldName = fieldName;
        this.a = a;
        this.b = b;
    }

    /**
     * フィールド名をセットする
     * 
     * @param fieldName フィールド名(変数名)
     */
    public final void setFieldName(final String fieldName) {
        this.fieldName = fieldName;
    }

    /**
     * オブジェクトaのフィールドパラメータをセットする
     * 
     * @param a フィールドパラメータ
     */
    public final void setLeft(final String a) {
        this.a = a;
    }

    /**
     * オブジェクトbのフィールドパラメータをセットする
     * 
     * @param b フィールドパラメータ
     */
    public final void setRight(final String b) {
        this.b = b;
    }

    /**
     * フィールド名(変数名)を取得する
     * 
     * @return フィールド名(変数名)
     */
    public final String getFieldName() {
        return this.fieldName;
    }

    /**
     * オブジェクトaのフィールドパラメータを取得する
     * 
     * @return フィールドパラメータ
     */
    public final Object getA() {
        return this.a;
    }

    /**
     * オブジェクトbのフィールドパラメータを取得する
     * 
     * @return フィールドパラメータ
     */
    public final Object getB() {
        return this.b;
    }
}