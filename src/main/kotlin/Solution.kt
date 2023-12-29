import kotlin.math.abs

/**
 *  왼쪽열 : 1,4,7
 *  오른쪽열 : 3,6,9
 *  가운데열 : 2,5,8,0 오른손잡이 : 오른손 엄지 왼손잡이 : 왼손 엄지
 *  시작점 : 왼손 * 오른손 #
 */

class Solution {
    private val keypad = arrayOf(
        intArrayOf(1, 2, 3),
        intArrayOf(4, 5, 6),
        intArrayOf(7, 8, 9),
        intArrayOf(-1, 0, -2) // -1 : * , -2 : #
    )

    private lateinit var leftThumb: Pair<Int, Int>
    private lateinit var rightThumb: Pair<Int, Int>
    fun findXY(num: Int): Pair<Int, Int> {
        var answer = Pair(0, 0)

        for (i in keypad.indices) {
            for (j in keypad[0].indices) {
                if (keypad[i][j] == num) return Pair(i, j)
            }
        }
        return answer
    }

    fun calculateDistance(pairA: Pair<Int, Int>, pairB: Pair<Int, Int>): Int {
        var answer: Int = 0
        var distanceX = abs(pairA.first - pairB.first)
        var distanceY = abs(pairA.second - pairB.second)

        answer = distanceX + distanceY

        return answer
    }

    fun solution(numbers: IntArray, hand: String): String {
        var answer = ""
        leftThumb = findXY(-1)
        rightThumb = findXY(-2)

        var leftDistance: Int = 0
        var rightDistance: Int = 0
        var isCheckedLeft = false
        numbers.forEach {

            var targetXY = findXY(it)

            when (it) {
                1, 4, 7 -> {
                    leftThumb = targetXY
                    isCheckedLeft = true
                }

                3, 6, 9 -> {
                    rightThumb = targetXY
                    isCheckedLeft = false
                }

                else -> {
                    //들어온 숫자의 거리계산
                    leftDistance = calculateDistance(targetXY, leftThumb)
                    rightDistance = calculateDistance(rightThumb, targetXY)
                    //거리가 같다면 hand 확인후 처리
                    if (leftDistance == rightDistance) {
                        if (hand == "right") {
                            rightThumb = targetXY
                            isCheckedLeft = false
                        } else {
                            leftThumb = targetXY
                            isCheckedLeft = true
                        }
                    } else if (leftDistance > rightDistance) {
                        rightThumb = targetXY
                        isCheckedLeft = false
                    } else {
                        leftThumb = targetXY
                        isCheckedLeft = true
                    }
                }
            }
            if (isCheckedLeft) answer += "L"
            else answer += "R"
        }
        return answer
    }
}

fun main() {
    var a = Solution()
    a.solution(intArrayOf(1, 3, 4, 5, 8, 2, 1, 4, 5, 9, 5), "right")//"LRLLLRLLRRL"
    a.solution(intArrayOf(7, 0, 8, 2, 8, 3, 1, 5, 7, 6, 2), "left")//"LRLLRRLLLRR"
    a.solution(intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 0), "right")//"LLRLLRLLRL"
}