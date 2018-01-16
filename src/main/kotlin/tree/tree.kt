package tree

import java.util.*

data class Node(
        val data: Int,
        val left: Node? = null,
        val right: Node? = null,
        val parent: Node? = null
) {
    val isLeaf: Boolean
        get() = left == null && right == null
}

fun inorderRecursive(node: Node?) {
    if (node != null) {
        inorderRecursive(node.left)
        println(node.data)
        inorderRecursive(node.right)
    }
}

fun searchRecursive(node: Node?, key: Int): Node? {
    return when {
        node == null -> null
        key == node.data -> node
        key < node.data -> searchRecursive(node.left, key)
        else -> searchRecursive(node.right, key)
    }
}

fun searchIterative(root: Node, key: Int): Node? {
    var curr: Node? = root
    while (curr != null && curr.data != key) {
        when {
            key < curr.data -> curr = curr.left
            key > curr.data -> curr = curr.right
        }
    }
    return curr
}

fun minRecursive(node: Node): Node {
    return when {
        node.left == null -> node
        else -> minRecursive(node.left)
    }
}

fun minIterative(root: Node): Node {
    var curr = root
    while (curr.left != null) {
        curr = curr.left!!
    }
    return curr
}

fun maxRecursive(node: Node): Node {
    return when {
        node.right == null -> node
        else -> maxRecursive(node.right)
    }
}

fun maxIterative(root: Node): Node {
    var curr = root
    var right = root.right
    while (right != null) {
        curr = right
        right = right.right
    }
    return curr
}

fun successor(node: Node): Node? {
    if (node.right != null) {
        return minIterative(node.right)
    }
    var curr = node
    var par = curr.parent
    while (par != null && par.left != curr) {
        curr = par
        par = par.parent
    }
    return par
}

fun predecessor(node: Node): Node? {
    if (node.left != null) {
        return maxIterative(node.left)
    }
    var curr = node
    var par = curr.parent
    while (par != null && par.right != curr) {
        curr = par
        par = par.parent
    }
    return par
}

fun postorderWithSingleStack(root: Node) {
    val stack = ArrayDeque<Node>()
    var curr: Node? = root
    while (curr != null || stack.isNotEmpty()) {
        if (curr != null) {
            stack.push(curr)
            curr = curr.left
        } else {
            val tmp = stack.peek().right
            if (tmp == null) {
                var top = stack.pop()
                println(top.data)
                while (stack.isNotEmpty() && stack.peek().right === top) {
                    top = stack.pop()
                    println(top.data)
                }
            } else {
                curr = tmp
            }
        }
    }
}

fun postorderWithSingleStack2(root: Node) {
    val stack = ArrayDeque<Node>()
    var curr: Node? = root
    while (stack.isNotEmpty() || curr != null) {
        if (curr != null) {
            stack.push(curr)
            curr = curr.left ?: curr.right
        } else {
            val top = stack.pop()
            println(top.data)
            if (stack.isEmpty()) {
                break
            }
            if (top !== stack.peek().right) {
                curr = stack.peek().right
            }
        }
    }
}

fun main(args: Array<String>) {
    val node6 = Node(6)
    val node5 = Node(5, right = node6)
    val node4 = Node(4)
    val node2 = Node(2, left = node4, right = node5)
    val node8 = Node(8)
    val node7 = Node(7, right = node8)
    val node3 = Node(3, right = node7)
    val root = Node(1, left = node2, right = node3)

    postorderWithSingleStack(root)
    println()
    postorderWithSingleStack2(root)
}
