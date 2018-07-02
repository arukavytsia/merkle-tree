package com.github.merkle.arbitrary

import com.github.merkle.{BlockView, MerkleTree}
import com.github.merkle.utils.UnitSpec
import com.github.merkle.conversion.Conversion._

class MerkleTreeArbitraryDigestSpec extends UnitSpec {

  it should "have equals root nodes" in {
    val blocks: Seq[BlockView] = Seq("Some", "Random", "String")
    val digest = "SHA-224"
    val first = MerkleTree.apply(blocks, digest)
    val second = MerkleTree.apply(blocks, digest)

    first.hash.left.get should be(second.hash.left.get)
  }

  it should "have equals root nodes: with odd leafs number" in {
    val blocks: Seq[BlockView] = Seq("AB", "RA", "CA", "DA", "BRA")
    val digest = "SHA-256"
    val second = MerkleTree.apply(blocks, digest)
    val first = MerkleTree.apply(blocks, digest)

    first.hash.left.get should be(second.hash.left.get)
  }

  it should "have different root nodes: with odd leafs number" in {
    val firstInput: Seq[BlockView] = Seq("AB11", "RA22", "CA33", "DA44", "BRA55")
    val secondInput: Seq[BlockView] = Seq("AB11", "RA22", "CA33", "DA44", "BRA55!!")
    val digest = "SHA-384"
    val first = MerkleTree.apply(firstInput, digest)
    val second = MerkleTree.apply(secondInput, digest)

    first.hash.left.get should not be second.hash.left.get
  }

  it should "have equals root nodes: input sequence of bytes with odd leafs number" in {
    val blocks: Seq[BlockView] = Seq(
      Array[Byte](59, 66, 1, 55),
      Array[Byte](42, 85, 49, 76),
      Array[Byte](34, -70, 10, 55),
      Array[Byte](5, 120, -104, 126),
      Array[Byte](81, 66, 4, 1)
    )

    val digest = "SHA-512"
    val first = MerkleTree.apply(blocks, digest)
    val second = MerkleTree.apply(blocks, digest)

    first.hash.left.get should be(second.hash.left.get)
  }

  it should "have equals root nodes: input sequence of bytes" in {
    val blocks: Seq[BlockView] = Seq(
      Array[Byte](44, 41, 42, 33),
      Array[Byte](14, 51, 51, 74),
      Array[Byte](33, 95, 50, 41),
      Array[Byte](-12, -122, 32, 44)
    )

    val digest = "SHA-512"
    val first = MerkleTree.apply(blocks, digest)
    val second = MerkleTree.apply(blocks, digest)

    first.hash.left.get should be(second.hash.left.get)
  }

  it should "have different root nodes" in {
    val firstInput: Seq[BlockView] = Seq("Ab", "ra", "ca", "da", "bra")
    val secondInput: Seq[BlockView] =  Seq("Ab", "ra", "ca", "da", "bra!")
    val digest = "SHA-384"

    val first = MerkleTree.apply(firstInput, digest)
    val second = MerkleTree.apply(secondInput, digest)

    first.hash.left.get should not be second.hash.left.get
  }

}
