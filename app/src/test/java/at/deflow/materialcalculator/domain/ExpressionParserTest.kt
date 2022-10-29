package at.deflow.materialcalculator.domain

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class ExpressionParserTest {

    private lateinit var parser: ExpressionParser

    @Test
    fun `Simple expression is properly parsed`() {

        parser = ExpressionParser("3+5-3x4/3")

        val actual = parser.parse()

        val expected = listOf(
            ExpressionPart.Number(3.0),
            ExpressionPart.Op(Operation.ADD),
            ExpressionPart.Number(5.0),
            ExpressionPart.Op(Operation.SUBTRACT),
            ExpressionPart.Number(3.0),
            ExpressionPart.Op(Operation.MULTIPLY),
            ExpressionPart.Number(4.0),
            ExpressionPart.Op(Operation.DIVIDE),
            ExpressionPart.Number(3.0),
        )
        assertThat(expected).isEqualTo(actual)
    }

    @Test
    fun `Expression with parentheses is properly parsed`() {
        parser = ExpressionParser("4-(4x5)")

        val actual = parser.parse()

        val expected = listOf(
            ExpressionPart.Number(4.0),
            ExpressionPart.Op(Operation.SUBTRACT),
            ExpressionPart.Parentheses(ParenthesesType.Opening),
            ExpressionPart.Number(4.0),
            ExpressionPart.Op(Operation.MULTIPLY),
            ExpressionPart.Number(5.0),
            ExpressionPart.Parentheses(ParenthesesType.Closing),
        )
        assertThat(expected).isEqualTo(actual)
    }

    @Test
    fun `Expression with parentheses is NOT properly parsed`() {
        parser = ExpressionParser("4-(4x5)")

        val actual = parser.parse()

        val expected = listOf(
            ExpressionPart.Number(2.0),
            ExpressionPart.Op(Operation.DIVIDE),
            ExpressionPart.Parentheses(ParenthesesType.Opening),
            ExpressionPart.Number(4.0),
            ExpressionPart.Op(Operation.MULTIPLY),
            ExpressionPart.Number(5.0),
            ExpressionPart.Parentheses(ParenthesesType.Closing),
        )
        assertThat(expected).isNotEqualTo(actual)
    }

    @Test
    fun `Complex Expression with parentheses and float numbers is properly parsed`() {
        parser = ExpressionParser("4.32/(4x5.05+(3.2-1.2))")

        val actual = parser.parse()

        val expected = listOf(
            ExpressionPart.Number(4.32),
            ExpressionPart.Op(Operation.DIVIDE),
            ExpressionPart.Parentheses(ParenthesesType.Opening),
            ExpressionPart.Number(4.0),
            ExpressionPart.Op(Operation.MULTIPLY),
            ExpressionPart.Number(5.05),
            ExpressionPart.Op(Operation.ADD),
            ExpressionPart.Parentheses(ParenthesesType.Opening),
            ExpressionPart.Number(3.2),
            ExpressionPart.Op(Operation.SUBTRACT),
            ExpressionPart.Number(1.2),
            ExpressionPart.Parentheses(ParenthesesType.Closing),
            ExpressionPart.Parentheses(ParenthesesType.Closing),
        )
        assertThat(expected).isEqualTo(actual)
    }
}