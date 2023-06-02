// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi
// Source File Name:   SMGPReader.java

package com.huawei.insa2.comm.smgp;

import com.huawei.insa2.comm.PMessage;
import com.huawei.insa2.comm.PReader;
import com.huawei.insa2.comm.smgp.message.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

public class SMGPReader extends PReader
{
    private static final Logger LOGGER = LoggerFactory.getLogger(SMGPReader.class);

    protected DataInputStream in;

    public SMGPReader(InputStream is)
    {
        in = new DataInputStream(is);
    }

    public PMessage read()
        throws IOException
    {
        int total_Length = in.readInt();
        int command_Id = in.readInt();
        LOGGER.debug("total_Length={},command_Id={}", total_Length, command_Id);
        byte buf[] = new byte[total_Length - 8];
        in.readFully(buf);
        LOGGER.debug("buf[].length={}", buf.length);
        if(command_Id == 0x80000001)
            return new SMGPLoginRespMessage(buf);
        if(command_Id == 3)
            return new SMGPDeliverMessage(buf);
        if(command_Id == 0x80000002)
            return new SMGPSubmitRespMessage(buf);
        if(command_Id == 0x80000005)
            return new SMGPForwardRespMessage(buf);
        if(command_Id == 0x80000007)
            return new SMGPQueryRespMessage(buf);
        if(command_Id == 0x80000004)
            return new SMGPActiveTestRespMessage(buf);
        if(command_Id == 4)
            return new SMGPActiveTestMessage(buf);
        if(command_Id == 6)
            return new SMGPExitMessage(buf);
        if(command_Id == 0x80000006)
            return new SMGPExitRespMessage(buf);
        if(command_Id == 0x80000008)
            return new SMGPMtRouteUpdateRespMessage(buf);
        if(command_Id == 0x80000009)
            return new SMGPMoRouteUpdateRespMessage(buf);
        else
            return null;
    }
}
