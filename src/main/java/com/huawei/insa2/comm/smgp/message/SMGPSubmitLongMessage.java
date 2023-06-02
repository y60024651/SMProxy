package com.huawei.insa2.comm.smgp.message;

import com.huawei.insa2.comm.smgp.SMGPConstant;
import com.huawei.insa2.util.TypeConvert;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 长短信
 * @author yinzhf
 * @since 2022-05-01 23:08
 */
public class SMGPSubmitLongMessage extends SMGPMessage {
    public static int TP_pid = 1;
    public static int TP_udhi = 2;
    public static int PkTotal = 9;
    public static int PkNumber = 10;
    public static int TLV_LENGTH = 5;
    private StringBuffer strBuf;

    public SMGPSubmitLongMessage(int msgType, int needReport, int priority, String serviceId, String feeType, String feeCode, String fixedFee, int msgFormat, Date validTime, Date atTime, String srcTermId, String chargeTermId, String[] destTermId, byte msgContent[], String reserve) throws IllegalArgumentException, UnsupportedEncodingException {
        if (msgType >= 0 && msgType <= 255) {
            if (needReport >= 0 && needReport <= 255) {
                if (priority >= 0 && priority <= 9) {
                    if (serviceId == null) {
                        throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SMGPConstant.SUBMIT_INPUT_ERROR)))).append(":ServiceID ").append(SMGPConstant.STRING_NULL))));
                    } else if (serviceId.length() > 10) {
                        throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SMGPConstant.SUBMIT_INPUT_ERROR)))).append(":ServiceID ").append(SMGPConstant.STRING_LENGTH_GREAT).append("10"))));
                    } else if (feeType == null) {
                        throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SMGPConstant.SUBMIT_INPUT_ERROR)))).append(":FeeType ").append(SMGPConstant.STRING_NULL))));
                    } else if (feeType.length() > 2) {
                        throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SMGPConstant.SUBMIT_INPUT_ERROR)))).append(":FeeType ").append(SMGPConstant.STRING_LENGTH_GREAT).append("2"))));
                    } else if (feeCode == null) {
                        throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SMGPConstant.SUBMIT_INPUT_ERROR)))).append(":FeeCode ").append(SMGPConstant.STRING_NULL))));
                    } else if (feeCode.length() > 6) {
                        throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SMGPConstant.SUBMIT_INPUT_ERROR)))).append(":FeeCode ").append(SMGPConstant.STRING_LENGTH_GREAT).append("6"))));
                    } else if (fixedFee == null) {
                        throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SMGPConstant.SUBMIT_INPUT_ERROR)))).append(":FixedFee ").append(SMGPConstant.STRING_NULL))));
                    } else if (fixedFee.length() > 6) {
                        throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SMGPConstant.SUBMIT_INPUT_ERROR)))).append(":FixedFee ").append(SMGPConstant.STRING_LENGTH_GREAT).append("6"))));
                    } else if (msgFormat >= 0 && msgFormat <= 255) {
                        if (srcTermId == null) {
                            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SMGPConstant.SUBMIT_INPUT_ERROR)))).append(":SrcTermID ").append(SMGPConstant.STRING_NULL))));
                        } else if (srcTermId.length() > 21) {
                            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SMGPConstant.SUBMIT_INPUT_ERROR)))).append(":SrcTermID ").append(SMGPConstant.STRING_LENGTH_GREAT).append("21"))));
                        } else if (chargeTermId == null) {
                            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SMGPConstant.SUBMIT_INPUT_ERROR)))).append(":ChargeTermID ").append(SMGPConstant.STRING_NULL))));
                        } else if (chargeTermId.length() > 21) {
                            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SMGPConstant.SUBMIT_INPUT_ERROR)))).append(":ChargeTermID ").append(SMGPConstant.STRING_LENGTH_GREAT).append("21"))));
                        } else if (destTermId == null) {
                            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SMGPConstant.SUBMIT_INPUT_ERROR)))).append(":DestTermID ").append(SMGPConstant.STRING_NULL))));
                        } else if (destTermId.length > 100) {
                            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SMGPConstant.SUBMIT_INPUT_ERROR)))).append(":").append(SMGPConstant.DESTTERMID_ERROR))));
                        } else {
                            for(int i = 0; i < destTermId.length; ++i) {
                                if (destTermId[i].length() > 21) {
                                    throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SMGPConstant.SUBMIT_INPUT_ERROR)))).append(":one DestTermID ").append(SMGPConstant.STRING_LENGTH_GREAT).append("21"))));
                                }
                            }
                            if(msgContent == null)
                                throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SMGPConstant.SUBMIT_INPUT_ERROR)))).append(":MsgContent ").append(SMGPConstant.STRING_NULL))));
                            if(msgContent.length > 252)
                                throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SMGPConstant.SUBMIT_INPUT_ERROR)))).append(":MsgContent ").append(SMGPConstant.STRING_LENGTH_GREAT).append("252"))));
                            if (reserve != null && reserve.length() > 8) {
                                throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SMGPConstant.SUBMIT_INPUT_ERROR)))).append(":reserve ").append(SMGPConstant.STRING_LENGTH_GREAT).append("8"))));
                            }
                            int len = 126 + 21 * destTermId.length + msgContent.length + TLV_LENGTH * 4;
                            super.buf = new byte[len];
                            TypeConvert.int2byte(len, super.buf, 0);
                            TypeConvert.int2byte(2, super.buf, 4);
                            super.buf[12] = (byte)msgType;
                            super.buf[13] = (byte)needReport;
                            super.buf[14] = (byte)priority;
                            System.arraycopy(serviceId.getBytes(), 0, super.buf, 15, serviceId.length());
                            System.arraycopy(feeType.getBytes(), 0, super.buf, 25, feeType.length());
                            System.arraycopy(feeCode.getBytes(), 0, super.buf, 27, feeCode.length());
                            System.arraycopy(fixedFee.getBytes(), 0, super.buf, 33, fixedFee.length());
                            super.buf[39] = (byte)msgFormat;
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMddHHmmss");
                            String tmpTime;
                            if (validTime != null) {
                                tmpTime = String.valueOf(String.valueOf(dateFormat.format(validTime))).concat("032+");
                                System.arraycopy(tmpTime.getBytes(), 0, super.buf, 40, 16);
                            }

                            if (atTime != null) {
                                tmpTime = String.valueOf(String.valueOf(dateFormat.format(atTime))).concat("032+");
                                System.arraycopy(tmpTime.getBytes(), 0, super.buf, 57, 16);
                            }

                            System.arraycopy(srcTermId.getBytes(), 0, super.buf, 74, srcTermId.length());
                            System.arraycopy(chargeTermId.getBytes(), 0, super.buf, 95, chargeTermId.length());
                            super.buf[116] = (byte)destTermId.length;
                            //int i = false;

                            int i;
                            for(i = 0; i < destTermId.length; ++i) {
                                System.arraycopy(destTermId[i].getBytes(), 0, super.buf, 117 + i * 21, destTermId[i].length());
                            }

                            int loc = 117 + i * 21;
                            super.buf[loc] = (byte)msgContent.length;
                            System.arraycopy(msgContent, 0, super.buf, loc + 1, msgContent.length);
                            loc = loc + 1 + msgContent.length;
                            if (reserve != null) {
                                System.arraycopy(reserve.getBytes(), 0, super.buf, loc, reserve.length());
                            }

                            loc += 8;
                            TypeConvert.int2byte2(TP_pid, super.buf, loc);
                            TypeConvert.int2byte2(1, super.buf, loc + 2);
                            super.buf[loc + 4] = 0;
                            loc += TLV_LENGTH;
                            TypeConvert.int2byte2(TP_udhi, super.buf, loc);
                            TypeConvert.int2byte2(1, super.buf, loc + 2);
                            super.buf[loc + 4] = 1;
                            loc += TLV_LENGTH;
                            TypeConvert.int2byte2(PkTotal, super.buf, loc);
                            TypeConvert.int2byte2(1, super.buf, loc + 2);
                            super.buf[loc + 4] = msgContent[4];
                            loc += TLV_LENGTH;
                            TypeConvert.int2byte2(PkNumber, super.buf, loc);
                            TypeConvert.int2byte2(1, super.buf, loc + 2);
                            super.buf[loc + 4] = msgContent[5];

                            this.strBuf = new StringBuffer(600);
                            this.strBuf.append(",MsgType=".concat(String.valueOf(String.valueOf(msgType))));
                            this.strBuf.append(",NeedReport=".concat(String.valueOf(String.valueOf(needReport))));
                            this.strBuf.append(",Priority=".concat(String.valueOf(String.valueOf(priority))));
                            this.strBuf.append(",ServiceID=".concat(String.valueOf(String.valueOf(serviceId))));
                            this.strBuf.append(",FeeType=".concat(String.valueOf(String.valueOf(feeType))));
                            this.strBuf.append(",FeeCode=".concat(String.valueOf(String.valueOf(feeCode))));
                            this.strBuf.append(",FixedFee=".concat(String.valueOf(String.valueOf(fixedFee))));
                            this.strBuf.append(",MsgFormat=".concat(String.valueOf(String.valueOf(msgFormat))));
                            if (validTime != null) {
                                this.strBuf.append(",ValidTime=".concat(String.valueOf(String.valueOf(dateFormat.format(validTime)))));
                            } else {
                                this.strBuf.append(",ValidTime=null");
                            }

                            if (atTime != null) {
                                this.strBuf.append(",AtTime=".concat(String.valueOf(String.valueOf(dateFormat.format(atTime)))));
                            } else {
                                this.strBuf.append(",at_Time=null");
                            }

                            this.strBuf.append(",SrcTermID=".concat(String.valueOf(String.valueOf(srcTermId))));
                            this.strBuf.append(",ChargeTermID=".concat(String.valueOf(String.valueOf(chargeTermId))));
                            this.strBuf.append(",DestTermIDCount=".concat(String.valueOf(String.valueOf(destTermId.length))));

                            for(int t = 0; t < destTermId.length; ++t) {
                                this.strBuf.append(String.valueOf(String.valueOf((new StringBuffer(",DestTermID[")).append(t).append("]=").append(destTermId[t]))));
                            }

                            this.strBuf.append(",MsgLength=".concat(String.valueOf(String.valueOf(msgContent.length))));
                            byte[] tempMsgBytes = new byte[msgContent.length - 6];
                            System.arraycopy(msgContent, 6, tempMsgBytes, 0, msgContent.length - 6);
                            String msg = 8 == msgFormat ? new String(tempMsgBytes, "ISO-10646-UCS-2") : 15 == msgFormat ? new String(tempMsgBytes, "GBK") : new String(tempMsgBytes);
                            this.strBuf.append(",MsgContent=".concat(msg));
                            this.strBuf.append(",Reserve=".concat(String.valueOf(String.valueOf(reserve))));
                        }
                    } else {
                        throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SMGPConstant.SUBMIT_INPUT_ERROR)))).append(":MsgFormat ").append(SMGPConstant.INT_SCOPE_ERROR))));
                    }
                } else {
                    throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SMGPConstant.SUBMIT_INPUT_ERROR)))).append(":").append(SMGPConstant.PRIORITY_ERROR))));
                }
            } else {
                throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SMGPConstant.SUBMIT_INPUT_ERROR)))).append(":NeedReport ").append(SMGPConstant.INT_SCOPE_ERROR))));
            }
        } else {
            throw new IllegalArgumentException(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(SMGPConstant.SUBMIT_INPUT_ERROR)))).append(":MsgType ").append(SMGPConstant.INT_SCOPE_ERROR))));
        }
    }

    public int getRequestId() {
        return 2;
    }

    public String toString() {
        StringBuffer outBuf = new StringBuffer(600);
        outBuf.append("SMGPSubmitLongMessage: ");
        outBuf.append("PacketLength=".concat(String.valueOf(String.valueOf(super.buf.length))));
        outBuf.append(",RequestID=2");
        outBuf.append(",SequenceID=".concat(String.valueOf(String.valueOf(this.getSequenceId()))));
        if (this.strBuf != null) {
            outBuf.append(this.strBuf.toString());
        }
        return outBuf.toString();
    }
}
