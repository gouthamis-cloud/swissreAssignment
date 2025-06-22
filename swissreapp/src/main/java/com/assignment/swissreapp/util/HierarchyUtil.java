package com.assignment.swissreapp.util;

import com.assignment.swissreapp.model.Employee;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HierarchyUtil {

    public static List<Map<String, Object>> buildHierarchy(List<Employee> employees) {
        Map<Long, Map<String, Object>> idToNode = new HashMap<>();
        List<Map<String, Object>> roots = new ArrayList<>();

        for (Employee emp : employees) {
            Map<String, Object> node = new HashMap<>();
            node.put("id", emp.getId());
            node.put("name", emp.getName());
            node.put("role", emp.getCategory());
            node.put("reportees", new ArrayList<Map<String, Object>>());
            idToNode.put(emp.getId(), node);
        }

        for (Employee emp : employees) {
            if (emp.getManagerId() == null) {
                roots.add(idToNode.get(emp.getId()));
            } else {
                Map<String, Object> mgrNode = idToNode.get(emp.getManagerId());
                if (mgrNode != null) {
                    List<Map<String, Object>> reportees = (List<Map<String, Object>>) mgrNode.get("reportees");
                    reportees.add(idToNode.get(emp.getId()));
                }
            }
        }

        return roots;
    }
}
