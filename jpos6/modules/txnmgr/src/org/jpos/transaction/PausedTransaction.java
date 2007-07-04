/*
 * Copyright (c) 2007 jPOS.org 
 *
 * See terms of license at http://jpos.org/license.html
 *
 */

package org.jpos.transaction;

import java.io.PrintStream;
import java.util.List;
import java.util.Iterator;
import java.util.TimerTask;
import org.jpos.util.Loggeable;

public class PausedTransaction implements Loggeable {
    private long id;
    private List members;
    private Iterator iter;
    private boolean aborting;
    private TransactionManager txnmgr;
    private boolean resumed;
    private TimerTask expirationMonitor;
    public PausedTransaction (
            TransactionManager txnmgr,
            long id, List members, Iterator iter, boolean aborting, TimerTask expirationMonitor) 
    {
        super();
        this.txnmgr = txnmgr;
        this.id = id;
        this.members = members;
        this.iter = iter;
        this.aborting = aborting;
        this.expirationMonitor = expirationMonitor;
    }
    public long id() {
        return id;
    }
    public List members() {
        return members;
    }
    public Iterator iterator() {
        return iter;
    }
    public void dump (PrintStream p, String indent) {
        p.println (indent + "id: " + id
                + (isAborting() ? " (aborting)" : ""));

    }
    public boolean isAborting() {
        return aborting;
    }
    public void forceAbort() {
        this.aborting = true;
    }
    public TransactionManager getTransactionManager() {
        return txnmgr;
    }
    public void setResumed (boolean resumed) {
        this.resumed = resumed;
    }
    public boolean isResumed() {
        return resumed;
    }
    public synchronized void cancelExpirationMonitor() {
        if (expirationMonitor != null)
            expirationMonitor.cancel();
    }
}
