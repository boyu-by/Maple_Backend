package com.example.maple.model;

import java.util.List;
import java.util.Map;

public class MindMapData {
    private List<Node> nodes;
    private List<String> rootIds;

    public List<Node> getNodes() {
        return nodes;
    }

    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }

    public List<String> getRootIds() {
        return rootIds;
    }

    public void setRootIds(List<String> rootIds) {
        this.rootIds = rootIds;
    }

    public static class Node {
        private String id;
        private String text;
        private String parentId;
        private List<String> childrenIds;
        private Map<String, Object> style;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getParentId() {
            return parentId;
        }

        public void setParentId(String parentId) {
            this.parentId = parentId;
        }

        public List<String> getChildrenIds() {
            return childrenIds;
        }

        public void setChildrenIds(List<String> childrenIds) {
            this.childrenIds = childrenIds;
        }

        public Map<String, Object> getStyle() {
            return style;
        }

        public void setStyle(Map<String, Object> style) {
            this.style = style;
        }
    }
}