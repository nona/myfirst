package org.myfirst.service;

import org.neo4j.graphdb.*;
import org.neo4j.graphdb.index.IndexManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class DBCleanerService {

	@Autowired private GraphDatabaseService graph;


    public Map<String, Object> cleanDb() {
        Map<String, Object> result = new HashMap<String, Object>();
        try (Transaction tx = graph.beginTx()) {
            removeNodes(result);
            clearIndex(result);
            tx.success();
        } 
        return result;
    }

    private void removeNodes(Map<String, Object> result) {
        int nodes = 0, relationships = 0;
        for (Node node : graph.getAllNodes()) {
            for (Relationship rel : node.getRelationships(Direction.OUTGOING)) {
                rel.delete();
                relationships++;
            }
            node.delete();
            nodes++;
        }
        result.put("nodes", nodes);
        result.put("relationships", relationships);

    }

    private void clearIndex(Map<String, Object> result) {
        IndexManager indexManager = graph.index();
        result.put("node-indexes", Arrays.asList(indexManager.nodeIndexNames()));
        result.put("relationship-indexes", Arrays.asList(indexManager.relationshipIndexNames()));
        for (String ix : indexManager.nodeIndexNames()) {
            indexManager.forNodes(ix).delete();
        }
        for (String ix : indexManager.relationshipIndexNames()) {
            indexManager.forRelationships(ix).delete();
        }
    }
}
