package com.finance.entity.resp;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * 树形表头节点：
 * - 叶子节点：label + pk
 * - 非叶子节点：label + _children
 */
public class HeaderNodeVO {

    @ApiModelProperty("表头显示文字")
    private String label;

    @ApiModelProperty("叶子节点的列标识（a/b/c...），只有叶子才有")
    private String pk;

    @ApiModelProperty("子节点列表（多级表头）")
    private List<HeaderNodeVO> _children;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getPk() {
        return pk;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }

    public List<HeaderNodeVO> get_children() {
        return _children;
    }

    public void set_children(List<HeaderNodeVO> _children) {
        this._children = _children;
    }
}

