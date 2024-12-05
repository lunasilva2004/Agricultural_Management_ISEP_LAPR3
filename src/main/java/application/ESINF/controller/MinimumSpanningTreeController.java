package application.ESINF.controller;

import application.ESINF.domain.Localidades;
import application.ESINF.functions.US_EI01_GraphBuilder;
import application.ESINF.functions.US_EI04_MinimumSpanningTree;
import application.ESINF.graph.map.MapGraph;

public class MinimumSpanningTreeController {
    private US_EI01_GraphBuilder graphBuilder;
    public MinimumSpanningTreeController(){ graphBuilder = US_EI01_GraphBuilder.getInstance();}
    public MapGraph<Localidades, Integer> calculateMinimumSpanningTree(){
        return US_EI04_MinimumSpanningTree.getMinimumSpanningTree(graphBuilder.getDistribuicao());
    }
}
