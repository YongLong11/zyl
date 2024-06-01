package com.zyl.utils.xmind;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Attached {

    private String id;
    /**
     * 主要内容
     */
    private String title;

    /**
     * 孩子节点
     */
    private Children children;
    /**
     * 父亲节点
     */
    private Attached parent;
    /**
     * 树根
     */
    private Attached rootTopic;
    /**
     * 树结构的孩子节点
     */
    private List<Attached> attachedChildren;
    /**
     * 是否为根节点
     */
    private boolean isRoot = false;
    /**
     * 是否为叶子节点
     */
    private boolean isLeaf = false;

}
