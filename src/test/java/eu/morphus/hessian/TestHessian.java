package eu.morphus.hessian;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Year;
import java.util.Locale;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;

class TestHessian {

	@Test
	public void testTime() throws Exception {
		assertSerializeAndDeserializeAndCompare(Year.of(2017));
	}

	@Test
	public void testSqlDate() throws Exception {
		assertSerializeAndDeserializeAndCompare(new java.sql.Date(1000));
	}

	@Test
	public void testString() throws Exception {
		assertSerializeAndDeserializeAndCompare("42");
	}

	@Test
	public void testBigDecimal() throws Exception {
		assertSerializeAndDeserializeAndCompare(new BigDecimal("42.22"));
	}

	@Test
	@Disabled
	public void testLocale() throws Exception {
		assertSerializeAndDeserializeAndCompare(Locale.GERMAN);
	}

	@Test
	public void testBigInteger() throws Exception {
		assertSerializeAndDeserializeAndCompare(new BigInteger("42"));
	}

	@Test
	public void testDouble() throws Exception {
		assertSerializeAndDeserializeAndCompare(42.22d);
	}

	@Test
	public void testInt() throws Exception {
		assertSerializeAndDeserializeAndCompare(42);
	}

	private void assertSerializeAndDeserializeAndCompare(Object toTest) throws Exception {

		// Writing the output with hessian
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		Hessian2Output out = new Hessian2Output(bos);
		out.startMessage();
		out.writeObject(toTest);
		out.completeMessage();
		out.close();

		// Reading byte array as input with hessian
		byte[] data = bos.toByteArray();
		ByteArrayInputStream bin = new ByteArrayInputStream(data);
		Hessian2Input in = new Hessian2Input(bin);
		in.startMessage();
		Object readObject = in.readObject();
		in.completeMessage();
		in.close();
		bin.close();

		// Asserting
		Assertions.assertEquals(toTest, readObject);
	}

}
