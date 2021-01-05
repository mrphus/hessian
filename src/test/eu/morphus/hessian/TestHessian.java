@Test
public void testHessianSerializeDeserialize() throws IOException {
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    Hessian2Output out = new Hessian2Output(bos);

    out.startMessage();
    Year y1 = Year.of(2017);
    out.writeObject(y1);

    out.completeMessage();
    out.close();

    byte[] data = bos.toByteArray();

    // Deserialize
    ByteArrayInputStream bin = new ByteArrayInputStream(data);
    Hessian2Input in = new Hessian2Input(bin);

    in.startMessage();
    Assert.assertEquals(y1, (Year) in.readObject());

    in.completeMessage();
    in.close();
    bin.close();
}
