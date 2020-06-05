package com.zyblue.fastim.common.algorithm.distribute;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author will.zhang
 * @date 2020/5/29 18:53
 *
 *
 * 链表实现思路：
 * 数据是直接利用 HashMap 来存放的。
 * 内部使用了一个双向链表来存放数据，所以有一个头结点 header，以及尾结点 tailer。
 * 每次写入头结点，删除尾结点时都是依赖于 header tailer，如果看着比较懵建议自己实现一个链表熟悉下，或结合下文的对象关系图一起理解。
 * 使用数据移动到链表头时，第一步是需要在双向链表中找到该节点。这里就体现出链表的问题了。查找效率很低，最差需要 O(N)。之后依赖于当前节点进行移动。
 * 在写入头结点时有判断链表大小等于 2 时需要删除初始化的头尾结点。这是因为初始化时候生成了两个双向节点，没有数据只是为了形成一个数据结构。当真实数据进来之后需要删除以方便后续的操作（这点可以继续优化）。
 * 以上的所有操作都是线程不安全的，需要使用者自行控制。
 */
public class LRUCacheV2 {

}
