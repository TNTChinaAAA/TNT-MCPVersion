package javax.jnlp;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public interface JNLPRandomAccessFile extends DataInput, DataOutput {
   void close() throws IOException;

   long length() throws IOException;

   long getFilePointer() throws IOException;

   int read() throws IOException;

   int read(byte[] var1, int var2, int var3) throws IOException;

   int read(byte[] var1) throws IOException;

   void readFully(byte[] var1) throws IOException;

   void readFully(byte[] var1, int var2, int var3) throws IOException;

   int skipBytes(int var1) throws IOException;

   boolean readBoolean() throws IOException;

   byte readByte() throws IOException;

   int readUnsignedByte() throws IOException;

   short readShort() throws IOException;

   int readUnsignedShort() throws IOException;

   char readChar() throws IOException;

   int readInt() throws IOException;

   long readLong() throws IOException;

   float readFloat() throws IOException;

   double readDouble() throws IOException;

   String readLine() throws IOException;

   String readUTF() throws IOException;

   void seek(long var1) throws IOException;

   void setLength(long var1) throws IOException;

   void write(int var1) throws IOException;

   void write(byte[] var1) throws IOException;

   void write(byte[] var1, int var2, int var3) throws IOException;

   void writeBoolean(boolean var1) throws IOException;

   void writeByte(int var1) throws IOException;

   void writeShort(int var1) throws IOException;

   void writeChar(int var1) throws IOException;

   void writeInt(int var1) throws IOException;

   void writeLong(long var1) throws IOException;

   void writeFloat(float var1) throws IOException;

   void writeDouble(double var1) throws IOException;

   void writeBytes(String var1) throws IOException;

   void writeChars(String var1) throws IOException;

   void writeUTF(String var1) throws IOException;
}
