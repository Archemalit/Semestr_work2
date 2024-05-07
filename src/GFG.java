class BinomialHeapNode {
    int key;
    int degree;
    BinomialHeapNode parent;
    BinomialHeapNode sibling;
    BinomialHeapNode child;

    public BinomialHeapNode(int value) {
        key = value;
        degree = 0;
        parent = null;
        sibling = null;
        child = null;
    }

    public BinomialHeapNode findMinNode() {
        BinomialHeapNode x = this;
        BinomialHeapNode y = this;
        int min = x.key;

        while (x != null) {
            if (x.key < min) {
                y = x;
                min = x.key;
            }

            x = x.sibling;
        }

        return y;
    }

    public BinomialHeapNode findANodeWithKey(int value) {
        BinomialHeapNode temp = this, node = null;

        while (temp != null) {
            if (temp.key == value) {
                node = temp;
                break;
            }

            if (temp.child == null)
                temp = temp.sibling;

            else {
                node = temp.child.findANodeWithKey(value);
                if (node == null)
                    temp = temp.sibling;
                else
                    break;
            }
        }

        return node;
    }
}

class BinomialHeap {
    private BinomialHeapNode nodes;
    private int size;

    public BinomialHeap() {
        nodes = null;
        size = 0;
    }

    public void insert(int value) {

        if (value > 0) {
            BinomialHeapNode temp
                    = new BinomialHeapNode(value);
            if (nodes == null) {
                nodes = temp;
                size = 1;
            } else {
                unionNodes(temp);
                size++;
            }
        }
    }

    private void merge(BinomialHeapNode binHeap) {
        BinomialHeapNode heap1 = nodes;
        BinomialHeapNode heap2 = binHeap;

        if (heap1 == null) {
            nodes = heap2;
        } else if (heap2 == null) {
            nodes = heap1;
        } else {
            BinomialHeapNode head;
            BinomialHeapNode heap1Next = heap1;
            BinomialHeapNode heap2Next = heap2;

            if (heap1.degree <= heap2.degree) {
                head = heap1;
                heap1Next = heap1Next.sibling;
            } else {
                head = heap2;
                heap2Next = heap2Next.sibling;
            }

            BinomialHeapNode tail = head;

            while (heap1Next != null && heap2Next != null) {
                if (heap1Next.degree <= heap2Next.degree) {
                    tail.sibling = heap1Next;
                    heap1Next = heap1Next.sibling;
                } else {
                    tail.sibling = heap2Next;
                    heap2Next = heap2Next.sibling;
                }

                tail = tail.sibling;
            }

            if (heap1Next != null) {
                tail.sibling = heap1Next;
            } else {
                tail.sibling = heap2Next;
            }

            nodes = head;
        }
    }

    private void unionNodes(BinomialHeapNode binHeap) {
        merge(binHeap);

        BinomialHeapNode prevTemp = null;
        BinomialHeapNode temp = nodes;
        BinomialHeapNode nextTemp = nodes.sibling;

        while (nextTemp != null) {
            if ((temp.degree != nextTemp.degree) || ((nextTemp.sibling != null) && (nextTemp.sibling.degree == temp.degree))) {
                prevTemp = temp;
                temp = nextTemp;
            } else {
                if (temp.key <= nextTemp.key) {
                    temp.sibling = nextTemp.sibling;
                    nextTemp.parent = temp;
                    nextTemp.sibling = temp.child;
                    temp.child = nextTemp;
                    temp.degree++;
                } else {
                    if (prevTemp == null) {
                        nodes = nextTemp;
                    } else {
                        prevTemp.sibling = nextTemp;
                    }
                    temp.parent = nextTemp;
                    temp.sibling = nextTemp.child;
                    nextTemp.child = temp;
                    nextTemp.degree++;
                    temp = nextTemp;
                }
            }
            nextTemp = temp.sibling;
        }
    }

    public int findMinimum() {
        return nodes.findMinNode().key;
    }

    public void delete(int value) {
        if ((nodes != null) && (nodes.findANodeWithKey(value) != null)) {
            decreaseKeyValue(value, findMinimum() - 1);
            extractMin();
        }
    }

    public void decreaseKeyValue(int old_value, int new_value) {
        BinomialHeapNode temp = nodes.findANodeWithKey(old_value);

        if (temp == null)
            return;
        temp.key = new_value;

        BinomialHeapNode tempParent = temp.parent;

        while ((tempParent != null) && (temp.key < tempParent.key)) {
            int z = temp.key;
            temp.key = tempParent.key;
            tempParent.key = z;

            temp = tempParent;
            tempParent = tempParent.parent;
        }
    }

    public int extractMin() {
        BinomialHeapNode x = nodes;
        BinomialHeapNode y = nodes;
        int min = x.key;
        BinomialHeapNode prev = null;
        BinomialHeapNode prevmin = null;
        while (x != null) {
            if (x.key < min) {
                y = x;
                min = x.key;
                prevmin = prev;

            }
            prev = x;
            x = x.sibling;
        }

        if (prevmin != null) {
            prevmin.sibling = y.sibling;
        } else {
            nodes = y.sibling;
        }

        merge(y.child);

        return min;

    }
}
