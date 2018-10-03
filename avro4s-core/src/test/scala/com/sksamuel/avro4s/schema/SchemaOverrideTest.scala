package com.sksamuel.avro4s.schema

import com.sksamuel.avro4s.{AvroSchema, SchemaFor}
import org.apache.avro.{Schema, SchemaBuilder}
import org.scalatest.{FunSuite, Matchers}

class SchemaOverrideTest extends FunSuite with Matchers {

  test("allow overriding built in implicits for a core type") {

    implicit val StringAsBytes = new SchemaFor[String] {
      override def schema: Schema = SchemaBuilder.builder().bytesType()
    }

    case class OverrideTest(s: String, i: Int)

    val expected = new org.apache.avro.Schema.Parser().parse(getClass.getResourceAsStream("/schema_override.json"))
    val schema = AvroSchema[OverrideTest]
    schema.toString(true) shouldBe expected.toString(true)
  }
}