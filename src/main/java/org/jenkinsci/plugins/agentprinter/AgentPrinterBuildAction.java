package org.jenkinsci.plugins.agentprinter;


import hudson.model.Action;
import hudson.model.Run;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import jenkins.model.RunAction2;
import jenkins.tasks.SimpleBuildStep;

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
public class AgentPrinterBuildAction implements SimpleBuildStep.LastBuildAction, RunAction2 {

    private HashSet<String> agentList = new HashSet<>();

    @Override
    public Collection<? extends Action> getProjectActions() {
//        return Collections.singleton(new AgentPrinterBuildAction());
        return Collections.EMPTY_LIST;
    }

    @Override
    public String getIconFileName() {
        return null;
    }

    @Override
    public String getDisplayName() {
        return null;
    }

    @Override
    public String getUrlName() {
        return null;
    }

    @Override
    public void onAttached(Run<?, ?> run) {
        
    }

    @Override
    public void onLoad(Run<?, ?> run) {
        
    }
    
    public void addAgent(String agent) {
        agentList.add(agent);
    }
    
    public Iterable<String> getAgents() {
        return agentList;
    }
    
    public String getFoo() {
        return "foo";
    }
}
