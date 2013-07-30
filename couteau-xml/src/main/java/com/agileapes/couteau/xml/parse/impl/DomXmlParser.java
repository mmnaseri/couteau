/*
 * Copyright (c) 2013. AgileApes (http://www.agileapes.scom/), and
 * associated organization.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this
 * software and associated documentation files (the "Software"), to deal in the Software
 * without restriction, including without limitation the rights to use, copy, modify,
 * merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to the following
 * conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies
 * or substantial portions of the Software.
 */

package com.agileapes.couteau.xml.parse.impl;

import com.agileapes.couteau.basics.api.Stringifiable;
import com.agileapes.couteau.graph.tree.node.TreeNode;
import com.agileapes.couteau.graph.tree.node.impl.DirectedTreeNode;
import com.agileapes.couteau.xml.error.XmlParseError;
import com.agileapes.couteau.xml.parse.XmlParser;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.InputStream;
import java.util.List;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (2013/7/29, 19:44)
 */
public class DomXmlParser implements XmlParser {

    private final DocumentBuilder builder;

    public DomXmlParser() throws ParserConfigurationException {
        this(DocumentBuilderFactory.newInstance().newDocumentBuilder());
    }

    public DomXmlParser(DocumentBuilder builder) throws ParserConfigurationException {
        this.builder = builder;
    }

    @Override
    public TreeNode parse(InputStream source) throws XmlParseError {
        return parseNode(getDocument(source).getDocumentElement());
    }

    protected Document getDocument(InputStream source) throws XmlParseError {
        final Document document;
        try {
            document = builder.parse(source);
        } catch (Exception e) {
            throw new XmlParseError("Failed to parse document", e);
        }
        return document;
    }

    private static String getNodeType(short nodeType) {
        if (nodeType == 1) {
            return "ELEMENT_NODE";
        } else if (nodeType == 2) {
            return "ATTRIBUTE_NODE";
        } else if (nodeType == 3) {
            return "TEXT_NODE";
        } else if (nodeType == 4) {
            return "CDATA_SECTION_NODE";
        } else if (nodeType == 5) {
            return "ENTITY_REFERENCE_NODE";
        } else if (nodeType == 6) {
            return "ENTITY_NODE";
        } else if (nodeType == 7) {
            return "PROCESSING_INSTRUCTION_NODE";
        } else if (nodeType == 8) {
            return "COMMENT_NODE";
        } else if (nodeType == 9) {
            return "DOCUMENT_NODE";
        } else if (nodeType == 10) {
            return "DOCUMENT_TYPE_NODE";
        } else if (nodeType == 11) {
            return "DOCUMENT_FRAGMENT_NODE";
        } else if (nodeType == 12) {
            return "NOTATION_NODE";
        } else {
            return null;
        }
    }

    private static TreeNode parseNode(Node node) {
        final DirectedTreeNode treeNode = new DirectedTreeNode(new Stringifiable<DirectedTreeNode>() {
            @Override
            public String toString(DirectedTreeNode object) {
                if (object == null) {
                    return "(x)";
                }
                final StringBuilder builder = new StringBuilder();
                builder.append(object.getAttribute("#nodeName")).append("[");
                int i = 0;
                for (String name : object.getAttributeNames()) {
                    if (i ++ > 0) {
                        builder.append(",");
                    }
                    builder.append("@").append(name);
                }
                builder.append("]{");
                final List<TreeNode> children = object.getChildren();
                for (i = 0; i < children.size(); i++) {
                    final TreeNode child = children.get(i);
                    if (i > 0) {
                        builder.append(",");
                    }
                    builder.append(child == null ? "(x)" : child);
                }
                builder.append("}");
                return builder.toString();
            }
        });
        treeNode.setAttribute("#nodeName", node.getNodeName());
        treeNode.setAttribute("#nodeValue", node.getNodeValue());
        treeNode.setAttribute("#namespace", node.getNamespaceURI());
        treeNode.setAttribute("#prefix", node.getPrefix());
        try {
            treeNode.setAttribute("#textContent", node.getTextContent());
        } catch (Error ignored) {}
        treeNode.setAttribute("#nodeType", getNodeType(node.getNodeType()));
        final NamedNodeMap attributes = node.getAttributes();
        if (attributes != null) {
            for (int i = 0; i < attributes.getLength(); i ++) {
                final Node item = attributes.item(i);
                treeNode.setAttribute(item.getNodeName(), item.getNodeValue());
            }
        }
        final NodeList children = node.getChildNodes();
        for (int i = 0; i < children.getLength(); i ++) {
            treeNode.addChild(parseNode(children.item(i)));
        }
        return treeNode;
    }

}