// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: InvocationResponseProto.proto

package com.zyblue.fastim.common.protobuf;

public final class InvocationResponseProto {
  private InvocationResponseProto() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface InvocationResProtoOrBuilder extends
      // @@protoc_insertion_point(interface_extends:protocol.InvocationResProto)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>required int32 code = 1;</code>
     */
    boolean hasCode();
    /**
     * <code>required int32 code = 1;</code>
     */
    int getCode();

    /**
     * <code>required string msg = 2;</code>
     */
    boolean hasMsg();
    /**
     * <code>required string msg = 2;</code>
     */
    String getMsg();
    /**
     * <code>required string msg = 2;</code>
     */
    com.google.protobuf.ByteString
        getMsgBytes();

    /**
     * <code>required string data = 3;</code>
     */
    boolean hasData();
    /**
     * <code>required string data = 3;</code>
     */
    String getData();
    /**
     * <code>required string data = 3;</code>
     */
    com.google.protobuf.ByteString
        getDataBytes();
  }
  /**
   * Protobuf type {@code protocol.InvocationResProto}
   */
  public  static final class InvocationResProto extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:protocol.InvocationResProto)
      InvocationResProtoOrBuilder {
  private static final long serialVersionUID = 0L;
    // Use InvocationResProto.newBuilder() to construct.
    private InvocationResProto(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private InvocationResProto() {
      msg_ = "";
      data_ = "";
    }

    @Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return this.unknownFields;
    }
    private InvocationResProto(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      this();
      if (extensionRegistry == null) {
        throw new NullPointerException();
      }
      int mutable_bitField0_ = 0;
      com.google.protobuf.UnknownFieldSet.Builder unknownFields =
          com.google.protobuf.UnknownFieldSet.newBuilder();
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            case 8: {
              bitField0_ |= 0x00000001;
              code_ = input.readInt32();
              break;
            }
            case 18: {
              com.google.protobuf.ByteString bs = input.readBytes();
              bitField0_ |= 0x00000002;
              msg_ = bs;
              break;
            }
            case 26: {
              com.google.protobuf.ByteString bs = input.readBytes();
              bitField0_ |= 0x00000004;
              data_ = bs;
              break;
            }
            default: {
              if (!parseUnknownField(
                  input, unknownFields, extensionRegistry, tag)) {
                done = true;
              }
              break;
            }
          }
        }
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(this);
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(
            e).setUnfinishedMessage(this);
      } finally {
        this.unknownFields = unknownFields.build();
        makeExtensionsImmutable();
      }
    }
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return InvocationResponseProto.internal_static_protocol_InvocationResProto_descriptor;
    }

    @Override
    protected FieldAccessorTable
        internalGetFieldAccessorTable() {
      return InvocationResponseProto.internal_static_protocol_InvocationResProto_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              InvocationResProto.class, Builder.class);
    }

    private int bitField0_;
    public static final int CODE_FIELD_NUMBER = 1;
    private int code_;
    /**
     * <code>required int32 code = 1;</code>
     */
    public boolean hasCode() {
      return ((bitField0_ & 0x00000001) != 0);
    }
    /**
     * <code>required int32 code = 1;</code>
     */
    public int getCode() {
      return code_;
    }

    public static final int MSG_FIELD_NUMBER = 2;
    private volatile Object msg_;
    /**
     * <code>required string msg = 2;</code>
     */
    public boolean hasMsg() {
      return ((bitField0_ & 0x00000002) != 0);
    }
    /**
     * <code>required string msg = 2;</code>
     */
    public String getMsg() {
      Object ref = msg_;
      if (ref instanceof String) {
        return (String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        String s = bs.toStringUtf8();
        if (bs.isValidUtf8()) {
          msg_ = s;
        }
        return s;
      }
    }
    /**
     * <code>required string msg = 2;</code>
     */
    public com.google.protobuf.ByteString
        getMsgBytes() {
      Object ref = msg_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (String) ref);
        msg_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int DATA_FIELD_NUMBER = 3;
    private volatile Object data_;
    /**
     * <code>required string data = 3;</code>
     */
    public boolean hasData() {
      return ((bitField0_ & 0x00000004) != 0);
    }
    /**
     * <code>required string data = 3;</code>
     */
    public String getData() {
      Object ref = data_;
      if (ref instanceof String) {
        return (String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        String s = bs.toStringUtf8();
        if (bs.isValidUtf8()) {
          data_ = s;
        }
        return s;
      }
    }
    /**
     * <code>required string data = 3;</code>
     */
    public com.google.protobuf.ByteString
        getDataBytes() {
      Object ref = data_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (String) ref);
        data_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    private byte memoizedIsInitialized = -1;
    @Override
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized == 1) return true;
      if (isInitialized == 0) return false;

      if (!hasCode()) {
        memoizedIsInitialized = 0;
        return false;
      }
      if (!hasMsg()) {
        memoizedIsInitialized = 0;
        return false;
      }
      if (!hasData()) {
        memoizedIsInitialized = 0;
        return false;
      }
      memoizedIsInitialized = 1;
      return true;
    }

    @Override
    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      if (((bitField0_ & 0x00000001) != 0)) {
        output.writeInt32(1, code_);
      }
      if (((bitField0_ & 0x00000002) != 0)) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 2, msg_);
      }
      if (((bitField0_ & 0x00000004) != 0)) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 3, data_);
      }
      unknownFields.writeTo(output);
    }

    @Override
    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (((bitField0_ & 0x00000001) != 0)) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt32Size(1, code_);
      }
      if (((bitField0_ & 0x00000002) != 0)) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(2, msg_);
      }
      if (((bitField0_ & 0x00000004) != 0)) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(3, data_);
      }
      size += unknownFields.getSerializedSize();
      memoizedSize = size;
      return size;
    }

    @Override
    public boolean equals(final Object obj) {
      if (obj == this) {
       return true;
      }
      if (!(obj instanceof InvocationResProto)) {
        return super.equals(obj);
      }
      InvocationResProto other = (InvocationResProto) obj;

      if (hasCode() != other.hasCode()) return false;
      if (hasCode()) {
        if (getCode()
            != other.getCode()) return false;
      }
      if (hasMsg() != other.hasMsg()) return false;
      if (hasMsg()) {
        if (!getMsg()
            .equals(other.getMsg())) return false;
      }
      if (hasData() != other.hasData()) return false;
      if (hasData()) {
        if (!getData()
            .equals(other.getData())) return false;
      }
      if (!unknownFields.equals(other.unknownFields)) return false;
      return true;
    }

    @Override
    public int hashCode() {
      if (memoizedHashCode != 0) {
        return memoizedHashCode;
      }
      int hash = 41;
      hash = (19 * hash) + getDescriptor().hashCode();
      if (hasCode()) {
        hash = (37 * hash) + CODE_FIELD_NUMBER;
        hash = (53 * hash) + getCode();
      }
      if (hasMsg()) {
        hash = (37 * hash) + MSG_FIELD_NUMBER;
        hash = (53 * hash) + getMsg().hashCode();
      }
      if (hasData()) {
        hash = (37 * hash) + DATA_FIELD_NUMBER;
        hash = (53 * hash) + getData().hashCode();
      }
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static InvocationResProto parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static InvocationResProto parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static InvocationResProto parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static InvocationResProto parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static InvocationResProto parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static InvocationResProto parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static InvocationResProto parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static InvocationResProto parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }
    public static InvocationResProto parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }
    public static InvocationResProto parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static InvocationResProto parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static InvocationResProto parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }

    @Override
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder() {
      return DEFAULT_INSTANCE.toBuilder();
    }
    public static Builder newBuilder(InvocationResProto prototype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
    }
    @Override
    public Builder toBuilder() {
      return this == DEFAULT_INSTANCE
          ? new Builder() : new Builder().mergeFrom(this);
    }

    @Override
    protected Builder newBuilderForType(
        BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    /**
     * Protobuf type {@code protocol.InvocationResProto}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:protocol.InvocationResProto)
        InvocationResProtoOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return InvocationResponseProto.internal_static_protocol_InvocationResProto_descriptor;
      }

      @Override
      protected FieldAccessorTable
          internalGetFieldAccessorTable() {
        return InvocationResponseProto.internal_static_protocol_InvocationResProto_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                InvocationResProto.class, Builder.class);
      }

      // Construct using com.zyblue.fastim.common.protobuf.InvocationResponseProto.InvocationResProto.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }

      private Builder(
          BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }
      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessageV3
                .alwaysUseFieldBuilders) {
        }
      }
      @Override
      public Builder clear() {
        super.clear();
        code_ = 0;
        bitField0_ = (bitField0_ & ~0x00000001);
        msg_ = "";
        bitField0_ = (bitField0_ & ~0x00000002);
        data_ = "";
        bitField0_ = (bitField0_ & ~0x00000004);
        return this;
      }

      @Override
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return InvocationResponseProto.internal_static_protocol_InvocationResProto_descriptor;
      }

      @Override
      public InvocationResProto getDefaultInstanceForType() {
        return InvocationResProto.getDefaultInstance();
      }

      @Override
      public InvocationResProto build() {
        InvocationResProto result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @Override
      public InvocationResProto buildPartial() {
        InvocationResProto result = new InvocationResProto(this);
        int from_bitField0_ = bitField0_;
        int to_bitField0_ = 0;
        if (((from_bitField0_ & 0x00000001) != 0)) {
          result.code_ = code_;
          to_bitField0_ |= 0x00000001;
        }
        if (((from_bitField0_ & 0x00000002) != 0)) {
          to_bitField0_ |= 0x00000002;
        }
        result.msg_ = msg_;
        if (((from_bitField0_ & 0x00000004) != 0)) {
          to_bitField0_ |= 0x00000004;
        }
        result.data_ = data_;
        result.bitField0_ = to_bitField0_;
        onBuilt();
        return result;
      }

      @Override
      public Builder clone() {
        return super.clone();
      }
      @Override
      public Builder setField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          Object value) {
        return super.setField(field, value);
      }
      @Override
      public Builder clearField(
          com.google.protobuf.Descriptors.FieldDescriptor field) {
        return super.clearField(field);
      }
      @Override
      public Builder clearOneof(
          com.google.protobuf.Descriptors.OneofDescriptor oneof) {
        return super.clearOneof(oneof);
      }
      @Override
      public Builder setRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          int index, Object value) {
        return super.setRepeatedField(field, index, value);
      }
      @Override
      public Builder addRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          Object value) {
        return super.addRepeatedField(field, value);
      }
      @Override
      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof InvocationResProto) {
          return mergeFrom((InvocationResProto)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(InvocationResProto other) {
        if (other == InvocationResProto.getDefaultInstance()) return this;
        if (other.hasCode()) {
          setCode(other.getCode());
        }
        if (other.hasMsg()) {
          bitField0_ |= 0x00000002;
          msg_ = other.msg_;
          onChanged();
        }
        if (other.hasData()) {
          bitField0_ |= 0x00000004;
          data_ = other.data_;
          onChanged();
        }
        this.mergeUnknownFields(other.unknownFields);
        onChanged();
        return this;
      }

      @Override
      public final boolean isInitialized() {
        if (!hasCode()) {
          return false;
        }
        if (!hasMsg()) {
          return false;
        }
        if (!hasData()) {
          return false;
        }
        return true;
      }

      @Override
      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        InvocationResProto parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (InvocationResProto) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }
      private int bitField0_;

      private int code_ ;
      /**
       * <code>required int32 code = 1;</code>
       */
      public boolean hasCode() {
        return ((bitField0_ & 0x00000001) != 0);
      }
      /**
       * <code>required int32 code = 1;</code>
       */
      public int getCode() {
        return code_;
      }
      /**
       * <code>required int32 code = 1;</code>
       */
      public Builder setCode(int value) {
        bitField0_ |= 0x00000001;
        code_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>required int32 code = 1;</code>
       */
      public Builder clearCode() {
        bitField0_ = (bitField0_ & ~0x00000001);
        code_ = 0;
        onChanged();
        return this;
      }

      private Object msg_ = "";
      /**
       * <code>required string msg = 2;</code>
       */
      public boolean hasMsg() {
        return ((bitField0_ & 0x00000002) != 0);
      }
      /**
       * <code>required string msg = 2;</code>
       */
      public String getMsg() {
        Object ref = msg_;
        if (!(ref instanceof String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          String s = bs.toStringUtf8();
          if (bs.isValidUtf8()) {
            msg_ = s;
          }
          return s;
        } else {
          return (String) ref;
        }
      }
      /**
       * <code>required string msg = 2;</code>
       */
      public com.google.protobuf.ByteString
          getMsgBytes() {
        Object ref = msg_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (String) ref);
          msg_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>required string msg = 2;</code>
       */
      public Builder setMsg(
          String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000002;
        msg_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>required string msg = 2;</code>
       */
      public Builder clearMsg() {
        bitField0_ = (bitField0_ & ~0x00000002);
        msg_ = getDefaultInstance().getMsg();
        onChanged();
        return this;
      }
      /**
       * <code>required string msg = 2;</code>
       */
      public Builder setMsgBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000002;
        msg_ = value;
        onChanged();
        return this;
      }

      private Object data_ = "";
      /**
       * <code>required string data = 3;</code>
       */
      public boolean hasData() {
        return ((bitField0_ & 0x00000004) != 0);
      }
      /**
       * <code>required string data = 3;</code>
       */
      public String getData() {
        Object ref = data_;
        if (!(ref instanceof String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          String s = bs.toStringUtf8();
          if (bs.isValidUtf8()) {
            data_ = s;
          }
          return s;
        } else {
          return (String) ref;
        }
      }
      /**
       * <code>required string data = 3;</code>
       */
      public com.google.protobuf.ByteString
          getDataBytes() {
        Object ref = data_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (String) ref);
          data_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>required string data = 3;</code>
       */
      public Builder setData(
          String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000004;
        data_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>required string data = 3;</code>
       */
      public Builder clearData() {
        bitField0_ = (bitField0_ & ~0x00000004);
        data_ = getDefaultInstance().getData();
        onChanged();
        return this;
      }
      /**
       * <code>required string data = 3;</code>
       */
      public Builder setDataBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000004;
        data_ = value;
        onChanged();
        return this;
      }
      @Override
      public final Builder setUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.setUnknownFields(unknownFields);
      }

      @Override
      public final Builder mergeUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.mergeUnknownFields(unknownFields);
      }


      // @@protoc_insertion_point(builder_scope:protocol.InvocationResProto)
    }

    // @@protoc_insertion_point(class_scope:protocol.InvocationResProto)
    private static final InvocationResProto DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new InvocationResProto();
    }

    public static InvocationResProto getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    @Deprecated public static final com.google.protobuf.Parser<InvocationResProto>
        PARSER = new com.google.protobuf.AbstractParser<InvocationResProto>() {
      @Override
      public InvocationResProto parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new InvocationResProto(input, extensionRegistry);
      }
    };

    public static com.google.protobuf.Parser<InvocationResProto> parser() {
      return PARSER;
    }

    @Override
    public com.google.protobuf.Parser<InvocationResProto> getParserForType() {
      return PARSER;
    }

    @Override
    public InvocationResProto getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_protocol_InvocationResProto_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_protocol_InvocationResProto_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    String[] descriptorData = {
      "\n\035InvocationResponseProto.proto\022\010protoco" +
      "l\"=\n\022InvocationResProto\022\014\n\004code\030\001 \002(\005\022\013\n" +
      "\003msg\030\002 \002(\t\022\014\n\004data\030\003 \002(\tB<\n!com.zyblue.f" +
      "astim.common.protobufB\027InvocationRespons" +
      "eProto"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
        new com.google.protobuf.Descriptors.FileDescriptor.    InternalDescriptorAssigner() {
          public com.google.protobuf.ExtensionRegistry assignDescriptors(
              com.google.protobuf.Descriptors.FileDescriptor root) {
            descriptor = root;
            return null;
          }
        };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        }, assigner);
    internal_static_protocol_InvocationResProto_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_protocol_InvocationResProto_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_protocol_InvocationResProto_descriptor,
        new String[] { "Code", "Msg", "Data", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}