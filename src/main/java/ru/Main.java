package ru;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        testMain();
        System.out.println("Test ok");


        Scanner scanner = new Scanner(System.in);

        final int m;
         int n = 0;

        List<Integer> nodes = new ArrayList<Integer>();
        int node;

        System.out.println("Type number of m = >");
        m = scanner.nextInt();
       do {
            System.out.println("Type number of nodes = >");
            n = scanner.nextInt();
       } while (n > Math.pow(2, m));

        for (int i = 0; i < n; i++) {
                do {
                    System.out.println("Type id of node = >");
                    node = scanner.nextInt();
                }
                while (node > Math.pow(2, m));

                nodes.add(node);
            }
            List<ChordNode> chordNodes = new ArrayList<ChordNode>();
        for (int i = 0; i < nodes.size(); i++) {
            chordNodes.add(new ChordNode(nodes.get(i), m, nodes));
        }

        chordNodes.forEach(chordNode -> {
            System.out.println("Node " + chordNode.getId());
            System.out.println(chordNode);
        });


//
//        chordNodes = addNode(6, nodes, m);
//
//        chordNodes.forEach(chordNode -> {
//            System.out.println("Node " + chordNode.getId());
//            System.out.println(chordNode);
//        });
//
//        chordNodes = deleteNode(6, nodes, m);
//
//        chordNodes.forEach(chordNode -> {
//            System.out.println("Node " + chordNode.getId());
//            System.out.println(chordNode);
//        });


        int tmp = 0;
        int tmp2 = 0;
        while (true)
        {
            System.out.println(" 1 Search by identifier \n 2 Add new new node  \n 3 Delete node \n 4 Show data");
            tmp = scanner.nextInt();
            switch (tmp){
                case (1):
                    System.out.println("Type index =>");
                    tmp2 = scanner.nextInt();
                    System.out.println(findSuccessor(tmp2, 0, chordNodes, m));
                    break;
                case (2):
                    System.out.println("Type index =>");
                    tmp2 = scanner.nextInt();
                    chordNodes = addNode(tmp2, nodes, m);
                    System.out.println("Node is added");
                    break;
                case (3):
                    System.out.println("Type index =>");
                    tmp2 = scanner.nextInt();
                    chordNodes=deleteNode(tmp2,  nodes, m);
                    System.out.println("Node is deleted");
                    break;
                case (4):
                    chordNodes.forEach(chordNode -> {
                        System.out.println("Node " + chordNode.getId());
                        System.out.println(chordNode);
                    });
                    break;
            }


        }


    }



    public static Integer findSuccessor(int id, int startNodeIndex, List<ChordNode> nodes, int m){
        Integer predecessor = findPredecessor(id, startNodeIndex, nodes, m);
        Integer result = nodes.stream().filter(e -> {
            return e.getId().equals(predecessor);
        }).findFirst().get().getSuccessor();
        return result;
    }


    private static Integer findPredecessor(int id, int startNodeIndex, List<ChordNode> nodes, int m){
        ChordNode node = nodes.get(startNodeIndex);
        Integer intNode = node.getId();
        Integer successor = node.getSuccessor();
        int loopCount = 0;
        while(notIn(id, intNode, successor, m)){
            loopCount++;
            if(loopCount > Math.pow(2, m)){
                return nodes.get(0).getPredecessor();
            }
            intNode = findClosest(id, intNode, nodes, m);
            final int finalIntNode = intNode;
            successor = nodes.stream().filter(e -> {
                return e.getId().equals(finalIntNode);
            }).findFirst().get().getSuccessor();
        }
        return intNode;
    }


    private static Integer findClosest(int id, int intNode, List<ChordNode> nodes, int m){
        ChordNode node = nodes.stream().filter(e -> {
            return e.getId().equals(intNode);
        }).findFirst().get();
        for(int i = nodes.size() - 1; i >= 0; i--){

            if(isIn(node.getFingerStarts().get(i).getNode(), node.getId(), id, m)){
                return node.getFingerStarts().get(i).getNode();
            }
        }
        return node.getId();
    }

    private static boolean notIn(int input, int a, int b, int m){
        if(b < a){
            b += Math.pow(2, m);
        }
        return !(input > a && input <= b);
    }

    private static boolean isIn(int input, int a, int b, int m){
        if(b < a){
            b += Math.pow(2, m);
            input += Math.pow(2, m);
        }
        return input > a && input < b;
    }

    public static List<ChordNode> addNode(Integer id, List<Integer> nodes, int m){
        if(!nodes.contains(id)){
            nodes.add(id);
        }

        List<ChordNode> result = new ArrayList<>();

        for (int i = 0; i < nodes.size(); i++) {
            result.add(new ChordNode(nodes.get(i), m, nodes));
        }

        return result;
    }

    public static List<ChordNode> deleteNode(Integer id, List<Integer> nodes, int m){
        if(nodes.contains(id)){
            nodes.remove(id);
        }

        List<ChordNode> result = new ArrayList<>();

        for (int i = 0; i < nodes.size(); i++) {
            result.add(new ChordNode(nodes.get(i), m, nodes));
        }

        return result;
    }
    private static void testMain(){
        List<ChordNode> chordNodes = new ArrayList<>();

        List<Integer> nodes = Arrays.asList(0, 1, 3);

        List<Integer> ids = Arrays.asList(0, 1, 3);

        List<Integer> successors = Arrays.asList(1, 3, 0);

        List<Integer> predecessors = Arrays.asList(3, 0, 1);

        List<List<Integer>> starts = Arrays.asList(Arrays.asList(1, 2, 4),
                Arrays.asList(2, 3, 5),
                Arrays.asList(4, 5, 7));

        List<List<List<Integer>>> intervals = Arrays.asList(Arrays.asList(Arrays.asList(1, 2),
                Arrays.asList(2, 4),
                Arrays.asList(4, 0)),
                Arrays.asList(Arrays.asList(2, 3),
                        Arrays.asList(3, 5),
                        Arrays.asList(5, 1)),
                Arrays.asList(Arrays.asList(4, 5),
                        Arrays.asList(5, 7),
                        Arrays.asList(7, 3)));
        List<List<Integer>> nodeList = Arrays.asList(Arrays.asList(1, 3, 0),
                Arrays.asList(3, 3, 0),
                Arrays.asList(0, 0, 0));

        int m = 3;
        for (int i = 0; i < nodes.size(); i++) {
            chordNodes.add(new ChordNode(nodes.get(i), m, nodes));
        }

        for(int i = 0; i < chordNodes.size(); i++){
            ChordNode node = chordNodes.get(i);
            assert node.getId().equals(ids.get(i));
            assert node.getPredecessor().equals(predecessors.get(i));
            assert node.getSuccessor().equals(successors.get(i));

            for(int j = 0; j < node.getFingerStarts().size(); j++){
                FingerTable fingerTable = node.getFingerStarts().get(j);

                assert fingerTable.getNode().equals(nodeList.get(i).get(j));
                assert fingerTable.getStart().equals(starts.get(i).get(j));
                assert fingerTable.getFrom().equals(intervals.get(i).get(j).get(0));
                assert fingerTable.getTo().equals(intervals.get(i).get(j).get(1));
            }

        }

        assert findSuccessor(2, 0, chordNodes, 3).equals(3);
        for(int i = 4; i < 8; i++){
            assert findSuccessor(i, 0, chordNodes, 3).equals(0);
        }

    }
}
