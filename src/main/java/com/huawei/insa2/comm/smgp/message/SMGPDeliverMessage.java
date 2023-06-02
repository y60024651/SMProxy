// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi
// Source File Name:   SMGPDeliverMessage.java

package com.huawei.insa2.comm.smgp.message;

import com.huawei.insa2.comm.smgp.SMGPConstant;
import com.huawei.insa2.util.TypeConvert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

// Referenced classes of package com.huawei.insa2.comm.smgp.message:
//            SMGPMessage

public class SMGPDeliverMessage extends SMGPMessage
{
    private static final Logger LOGGER = LoggerFactory.getLogger(SMGPDeliverMessage.class);

    public SMGPDeliverMessage(byte buf[])
        throws IllegalArgumentException
    {
        int len = 81 + (buf[72] & 0xff);
        LOGGER.debug("len={}, buf.length={}", len, buf.length);
        // 因为中国电信短消息网关协议文档陈旧，MO短信实际包长度大于文档上约定的长度，
        // 所以这里取len，而不是实际包长度
        if (buf.length < len) {
            throw new IllegalArgumentException(SMGPConstant.SMC_MESSAGE_ERROR);
        } else {
            super.buf = new byte[len];
            System.arraycopy(buf, 0, super.buf, 0, len);
            sequence_Id = TypeConvert.byte2int(super.buf, 0);
            return;
        }
//        if(buf.length != len)
//        {
//            throw new IllegalArgumentException(SMGPConstant.SMC_MESSAGE_ERROR);
//        } else
//        {
//            super.buf = new byte[len];
//            System.arraycopy(buf, 0, super.buf, 0, buf.length);
//            sequence_Id = TypeConvert.byte2int(super.buf, 0);
//            return;
//        }
    }

    public byte[] getMsgId()
    {
        byte msgId[] = new byte[10];
        System.arraycopy(buf, 4, msgId, 0, 10);
        return msgId;
    }

    public int getIsReport()
    {
        return buf[14];
    }

    public int getMsgFormat()
    {
        return buf[15];
    }

    public Date getRecvTime()
    {
        Date date;
        try
        {
            byte tmpbyte[] = new byte[4];
            System.arraycopy(buf, 16, tmpbyte, 0, 4);
            String tmpstr = new String(tmpbyte);
            int tmpYear = Integer.parseInt(tmpstr);
            tmpbyte = new byte[2];
            System.arraycopy(buf, 20, tmpbyte, 0, 2);
            tmpstr = new String(tmpbyte);
            int tmpMonth = Integer.parseInt(tmpstr) - 1;
            System.arraycopy(buf, 22, tmpbyte, 0, 2);
            tmpstr = new String(tmpbyte);
            int tmpDay = Integer.parseInt(tmpstr);
            System.arraycopy(buf, 24, tmpbyte, 0, 2);
            tmpstr = new String(tmpbyte);
            int tmpHour = Integer.parseInt(tmpstr);
            System.arraycopy(buf, 26, tmpbyte, 0, 2);
            tmpstr = new String(tmpbyte);
            int tmpMinute = Integer.parseInt(tmpstr);
            System.arraycopy(buf, 28, tmpbyte, 0, 2);
            tmpstr = new String(tmpbyte);
            int tmpSecond = Integer.parseInt(tmpstr);
            Calendar calendar = Calendar.getInstance();
            calendar.set(tmpYear, tmpMonth, tmpDay, tmpHour, tmpMinute, tmpSecond);
            Date date1 = calendar.getTime();
            return date1;
        }
        catch(Exception e)
        {
            date = null;
        }
        return date;
    }

    public String getSrcTermID()
    {
        byte srcTermId[] = new byte[21];
        System.arraycopy(buf, 30, srcTermId, 0, 21);
        return (new String(srcTermId)).trim();
    }

    public String getDestTermID()
    {
        byte destTermId[] = new byte[21];
        System.arraycopy(buf, 51, destTermId, 0, 21);
        return (new String(destTermId)).trim();
    }

    public int getMsgLength()
    {
        return buf[72] & 0xff;
    }

    public byte[] getMsgContent()
    {
        int len = buf[72] & 0xff;
        byte content[] = new byte[len];
        System.arraycopy(buf, 73, content, 0, len);
        return content;
    }

    /**
     * 状态报告对应原短消息的MsgID
     */
    public byte[] getMsgIdOfMsgContent() {
        if (getIsReport() == 1) {
            byte[] id = new byte[10];
            System.arraycopy(getMsgContent(), 3, id, 0 , 10);
            return id;
        } else {
            return null;
        }
    }

    public String getSub() {
        if (getIsReport() == 1) {
            byte[] sub = new byte[3];
            System.arraycopy(getMsgContent(), 18, sub, 0 , 3);
            return (new String(sub)).trim();
        } else {
            return null;
        }
    }

    public String getDlvrd() {
        if (getIsReport() == 1) {
            byte[] dlvrd = new byte[3];
            System.arraycopy(getMsgContent(), 28, dlvrd, 0 , 3);
            return (new String(dlvrd)).trim();
        } else {
            return null;
        }
    }

    public String getSubmitDate() {
        if (getIsReport() == 1) {
            byte[] submitDate = new byte[10];
            System.arraycopy(getMsgContent(), 44, submitDate, 0 , 10);
            return (new String(submitDate)).trim();
        } else {
            return null;
        }
    }

    public String getDoneDate() {
        if (getIsReport() == 1) {
            byte[] doneDate = new byte[10];
            System.arraycopy(getMsgContent(), 65, doneDate, 0 , 10);
            return (new String(doneDate)).trim();
        } else {
            return null;
        }
    }

    public String getStat() {
        if (getIsReport() == 1) {
            byte[] stat = new byte[7];
            System.arraycopy(getMsgContent(), 81, stat, 0 , 7);
            return (new String(stat)).trim();
        } else {
            return null;
        }
    }

    public String getErr() {
        if (getIsReport() == 1) {
            byte[] err = new byte[3];
            System.arraycopy(getMsgContent(), 93, err, 0 , 3);
            return (new String(err)).trim();
        } else {
            return null;
        }
    }

    public byte[] getTxt() {
        if (getIsReport() == 1) {
            byte[] txt = new byte[20];
            System.arraycopy(getMsgContent(), 101, txt, 0 , 20);
            return txt;
        } else {
            return null;
        }
    }

    public String getReserve()
    {
        int loc = 73 + (buf[72] & 0xff);
        byte reserve[] = new byte[8];
        System.arraycopy(buf, loc, reserve, 0, 8);
        return (new String(reserve)).trim();
    }

    public String toString()
    {
        StringBuffer strBuf = new StringBuffer(600);
        strBuf.append("SMGPDeliverMessage: ");
        strBuf.append("Sequence_Id=".concat(String.valueOf(String.valueOf(getSequenceId()))));
        strBuf.append(",MsgID=".concat(TypeConvert.bytesToHexString(getMsgId(), 10)));
        strBuf.append(",IsReport=".concat(String.valueOf(String.valueOf(getIsReport()))));
        strBuf.append(",MsgFormat=".concat(String.valueOf(String.valueOf(getMsgFormat()))));
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        if(getRecvTime() != null)
            strBuf.append(",RecvTime=".concat(String.valueOf(String.valueOf(dateFormat.format(getRecvTime())))));
        else
            strBuf.append(",RecvTime=null");
        strBuf.append(",SrcTermID=".concat(String.valueOf(String.valueOf(getSrcTermID()))));
        strBuf.append(",DestTermID=".concat(String.valueOf(String.valueOf(getDestTermID()))));
        strBuf.append(",MsgLength=".concat(String.valueOf(String.valueOf(getMsgLength()))));
        // strBuf.append(",MsgContent=".concat(String.valueOf(String.valueOf(new String(getMsgContent())))));
        // 状态报告
        if (getIsReport() == 1) {
            strBuf.append(",MsgContent.Id=".concat(TypeConvert.bytesToHexString(getMsgIdOfMsgContent(), 10)));
            strBuf.append(",MsgContent.sub=".concat(getSub()));
            strBuf.append(",MsgContent.Dlvrd=".concat(getDlvrd()));
            strBuf.append(",MsgContent.Submit_date=".concat(getSubmitDate()));
            strBuf.append(",MsgContent.done_date=".concat(getDoneDate()));
            strBuf.append(",MsgContent.Stat=".concat(getStat()));
            strBuf.append(",MsgContent.Err=".concat(getErr()));
            strBuf.append(",MsgContent.txt=".concat(String.valueOf(String.valueOf(new String(getTxt())))));
        } else {
            try {
                String msgContent = 8 == getMsgFormat() ? new String(getMsgContent(), "ISO-10646-UCS-2") : 15 == getMsgFormat() ? new String(getMsgContent(), "GBK") : new String(getMsgContent());
                strBuf.append(",MsgContent=".concat(msgContent));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        strBuf.append(",reserve=".concat(String.valueOf(String.valueOf(getReserve()))));
        return strBuf.toString();
    }

    public int getRequestId()
    {
        return 3;
    }
}
