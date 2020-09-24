package com.gn5r.common.resource;

/**
 * オブジェクト比較リソースクラス
 * 
 * @author gn5r
 * @since 0.1.2-RELEASE
 */
public final class Difference {

    private String fieldName;
    private Object left;
    private Object right;

    /**
     * 引数なしのコンストラクタ
     */
    public Difference() {

    }

    /**
     * 引数ありのコンストラクタ
     * 
     * @param fieldName フィールド名(変数名)
     * @param left      第1引数のフィールドパラメータ
     * @param right     第2引数のフィールドパラメータ
     */
    public Difference(final String fieldName, final Object left, final Object right) {
        this.fieldName = fieldName;
        this.left = left;
        this.right = right;
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
     * 第1引数の指定したフィールドに格納されている値をセットする
     * 
     * @param left フィールドパラメータ
     */
    public final void setLeft(final String left) {
        this.left = left;
    }

    /**
     * 第2引数の指定したフィールドに格納されている値をセットする
     * 
     * @param right フィールドパラメータ
     */
    public final void setRight(final String right) {
        this.right = right;
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
     * 第1引数の指定したフィールドに格納されている値を取得する
     * 
     * @return フィールドパラメータ
     */
    public final Object getLeft() {
        return this.left;
    }

    /**
     * 第2引数の指定したフィールドに格納されている値を取得する
     * 
     * @return フィールドパラメータ
     */
    public final Object getRight() {
        return this.right;
    }
}