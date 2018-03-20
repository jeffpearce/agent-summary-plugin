package org.jenkinsci.plugins.agentprinter;

import hudson.Extension;
import hudson.model.Queue;
import hudson.model.Run;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.CheckForNull;
import org.jenkinsci.plugins.workflow.actions.WorkspaceAction;
import org.jenkinsci.plugins.workflow.flow.FlowExecution;
import org.jenkinsci.plugins.workflow.flow.GraphListener;
import org.jenkinsci.plugins.workflow.graph.FlowNode;

/*
 * The MIT License
 *
 * Copyright 2017 jxpearce.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
/**
 *
 * @author jxpearce
 */
@Extension
public class AgentPrinterGraphListener implements GraphListener {

    @Override
    public void onNewHead(FlowNode fn) {
        WorkspaceAction workspaceAction = workspaceAction(fn);
        if (workspaceAction != null) {
            String nodeName = workspaceAction.getNode();
            if (nodeName.equals("")) {
                nodeName = "master";
            }
            AgentPrinterBuildAction buildAction = actionFor(fn);
            buildAction.addAgent(nodeName);
        }
    }
    
    private static WorkspaceAction workspaceAction(FlowNode fn) {
      
        WorkspaceAction workspaceAction = fn.getAction(WorkspaceAction.class);
        
        while (workspaceAction == null && !fn.getParents().isEmpty()) {
            fn = fn.getParents().get(0);
            workspaceAction = fn.getAction(WorkspaceAction.class);
        }
      
        return workspaceAction;
    }

    private static AgentPrinterBuildAction actionFor(FlowNode fn) {
        AgentPrinterBuildAction action = null;
        FlowExecution fe = fn.getExecution();
        Run<?, ?> run = runFor(fe);
        if (run != null) {
            action = run.getAction(AgentPrinterBuildAction.class);
            if (action == null) {
                action = new AgentPrinterBuildAction();
                run.addAction(action);
            }
        }

        return action;
    }

    private static @CheckForNull
    Run<?, ?> runFor(FlowExecution exec) {
        Queue.Executable executable;
        try {
            executable = exec.getOwner().getExecutable();
        } catch (IOException x) {
            return null;
        }
        if (executable instanceof Run) {
            return (Run<?, ?>) executable;
        } else {
            return null;
        }
    }

}
