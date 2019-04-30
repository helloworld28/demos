package com.jim.java8;

import java.util.*;
import java.util.stream.Collectors;


public class PathFinderDFS_BFS {
    List<Segment> paths;

    public List<Segment> getPaths() {
        return paths;
    }

    public void setPaths(List<Segment> paths) {
        this.paths = paths;
    }


    static class Segment {
        private int id;

        private String start;
        private String end;

        private boolean visited;

        public Segment() {
        }

        public Segment(String start, String end) {
            this.start = start;
            this.end = end;
        }


        public String getStart() {
            return start;
        }

        public String getEnd() {
            return end;
        }


        public boolean isVisited() {
            return visited;
        }

        public void setVisited(boolean visited) {
            this.visited = visited;
        }

        @Override
        public String toString() {
            return "Path{" +
                    "id=" + id +
                    ", start='" + start + '\'' +
                    ", end='" + end + '\'' +
                    '}';
        }

        public void setId(int id) {
            this.id = id;
        }
    }


    public static List<Segment> generatePath3() {
        //生成测试地图2路段配置
        List<Segment> paths = new ArrayList<>();

        List<List<Integer>> pointss = new ArrayList<>();
        for (Integer x : Arrays.asList(14, 17)) {
            List<Integer> points = new ArrayList<>();
            for (int y = 2; y <= 10; y++) {
                points.add(x * 1000 + y);
            }
            pointss.add(points);
        }

        for (Integer y : Arrays.asList(6, 7, 8)) {
            List<Integer> points = new ArrayList<>();
            for (int x = 2; x <= 6; x++) {
                points.add(x * 1000 + y);
            }
            pointss.add(points);
        }

        //双向的点
        for (List<Integer> pointList : pointss) {
            Integer prePoint = pointList.get(0);
            for (int i = 1; i < pointList.size(); i++) {
                paths.add(new Segment(prePoint.toString(), pointList.get(i).toString()));
                paths.add(new Segment(pointList.get(i).toString(), prePoint.toString()));
                prePoint = pointList.get(i);
            }
        }


        Integer prePoint = 7002;
        for (int y = 3; y <= 10; y++) {
            Integer point = 7 * 1000 + y;
            paths.add(new Segment(prePoint.toString(), point.toString()));
            prePoint = point;
        }

        prePoint = 8010;
        for (int y = 9; y >= 3; y--) {
            Integer point = 8 * 1000 + y;
            paths.add(new Segment(prePoint.toString(), point.toString()));
            prePoint = point;
        }

        prePoint = 17002;
        for (int x = 16; x >= 7; x--) {
            Integer point = x * 1000 + 2;
            paths.add(new Segment(prePoint.toString(), point.toString()));
            prePoint = point;
        }


        prePoint = 8003;
        for (int x = 9; x <= 17; x++) {
            Integer point = x * 1000 + 3;
            paths.add(new Segment(prePoint.toString(), point.toString()));
            prePoint = point;
        }


        //左边的库位路段
        for (Integer x : Arrays.asList(13, 16)) {
            for (int y = 5; y <= 8; y++) {
                Integer entryPoint = (x + 1) * 1000 + y + 2;
                Integer holderPoint = (x) * 1000 + y;
                Integer exitPoint = (x + 1) * 1000 + y - 2;
                paths.add(new Segment(entryPoint.toString(), holderPoint.toString()));
                paths.add(new Segment(holderPoint.toString(), exitPoint.toString()));
            }
        }

        //右边库位
        for (Integer x : Arrays.asList(15, 18)) {
            for (int y = 5; y <= 8; y++) {
                Integer entryPoint = (x - 1) * 1000 + y + 2;
                Integer holderPoint = (x) * 1000 + y;
                Integer exitPoint = (x - 1) * 1000 + y - 2;
                paths.add(new Segment(entryPoint.toString(), holderPoint.toString()));
                paths.add(new Segment(holderPoint.toString(), exitPoint.toString()));
            }
        }


        //加入入口
        for (Integer y : Arrays.asList(4, 5, 6, 7, 8)) {
            Integer entryPoint = 7 * 1000 + y + 2;
            Integer holderPoint = 6 * 1000 + y;
            Integer exitPoint = 8 * 1000 + y - 2;
            paths.add(new Segment(entryPoint.toString(), holderPoint.toString()));
            paths.add(new Segment(holderPoint.toString(), exitPoint.toString()));
        }
        return paths;
    }

    public static void main(String[] args) {
        List<Segment> paths = generatePath3();
        Map<String, List<Segment>> startToPath = paths.stream().collect(Collectors.groupingBy(Segment::getStart));
        System.out.println(startToPath);

/*
        DFSPathFinder pathFinder = new DFSPathFinder(startToPath, "13006");
        Path path = new Path();
        path.start = "6007";
        path.end = "8006";
        pathFinder.findPath(path);*/


        BFSPathFinder bfsPathFinder = new BFSPathFinder(startToPath, "13006");
        Segment path = new Segment();
        path.start = "6007";
        path.end = "8006";
        bfsPathFinder.find(path);


    }


    /**
     * 深度优化查找
     * 主要使用一个栈来实现和一个记录是否访问列表来实现
     * 取到一点就加入栈中,如果这个点到达目标点结束,如果这个点还有下一级,然后递归
     */
    static class DFSPathFinder {


        Map<String, List<Segment>> adjacency;

        Stack stack = new Stack();

        private String targetNode;

        public DFSPathFinder(Map<String, List<Segment>> startToPath, String targetNode) {
            this.adjacency = startToPath;
            this.targetNode = targetNode;
        }

        public DFSPathFinder(Map<String, List<Segment>> startToPath) {
            this.adjacency = startToPath;
        }


        public boolean findPath(Segment currentSegment) {

            stack.push(currentSegment);
            currentSegment.setVisited(true);
            if (currentSegment.getEnd().equals(targetNode)) {
                return true;
            }
            for (Segment path : adjacency.get(currentSegment.getEnd())) {
                if (!path.isVisited()) {
                    if (findPath(path)) {
                        return true;
                    } else {
                        stack.pop();
                    }
                }
            }
            return false;

        }

        public Stack getStack() {
            return stack;
        }
    }


    /**
     * 广度优先查找最短路径
     * 使用一个队列,这个队列表示需要遍历的点
     * 每次从这个队列里取出头节点,然后把这个点标记为已访问
     * 然后把循环这个点的下一级邻接点,判断是否已经是目标点,如果是就结束查找,否则把这个点加入队列
     * 其实就是类似于的树的遍历,从根节点开始,一层层节点往下遍历
     * 为了记录到达目标点的所经过的节点,可以建立有记录上个节点的实体来追溯
     */
    static class BFSPathFinder {
        Map<String, List<Segment>> adjacency;

        String target;

        public BFSPathFinder(Map<String, List<Segment>> adjacency, String target) {
            this.adjacency = adjacency;
            this.target = target;
        }

        List<Segment> visited = new ArrayList<>();

        Queue<Node> queue = new LinkedList<>();

        void find(Segment path) {

            queue.offer(new Node(null, path));

            while (!queue.isEmpty()) {
                Node node = queue.poll();
                visited.add(node.current);
                for (Segment segment : adjacency.get(node.current.end)) {

                    if (segment.getEnd().equals(target)) {
                        System.out.println("找到目标点:" + segment);
                        while (node != null) {
                            System.out.println(node.current);
                            node = node.pre;
                        }
                        return;
                    }
                    if (!visited.contains(segment)) {

                        queue.offer(new Node(node, segment));
                    }
                }
            }
        }
    }

    static class Node {
        Node pre;
        Segment current;


        public Node(Node pre, Segment next) {
            this.pre = pre;
            this.current = next;
        }
    }


}