package com.ottamotta.mozoli

class Problem {

    val id: String? = null
    val name: String? = null
    val photoId1: String? = null
    val photoId2: String? = null
    val photoId3: String? = null
    val comment: String? = null
    val redpointPoints: Int? = null
    val flashPoints: Int? = null
    val holdsColor: String? = null
    val stickerColor: String? = null
    val grade: Grade? = null
    val creatorId: String? = null
    val creatorFirstName: String? = null
    val creatorLastName: String? = null
    val creatorUserName: String? = null
    val creatorAva: String? = null
    val likesNumber: Int? = null
    val isRequestingUserLikeIt: Boolean = false
    val eventId: String? = null
    val eventName: String? = null
    val requestingUserSolving: Solution? = null
    val gymId: String? = null
    val sector: Sector? = null
    val createdDate: String? = null

    fun solutionToSubmit(isFlash: Boolean = true, isRedpoint: Boolean = false, attempts: Int? = null): Solution {
        val solution = this.requestingUserSolving ?: Solution()
        return solution.apply {
            this.problemId = this@Problem.id
            this.isFlash = isFlash
            this.isRedpoint = isRedpoint
            this.attemptsNumber = attempts
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as Problem

        if (id != other.id) return false
        if (likesNumber != other.likesNumber) return false
        if (isRequestingUserLikeIt != other.isRequestingUserLikeIt) return false
        if (requestingUserSolving != other.requestingUserSolving) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + (likesNumber ?: 0)
        result = 31 * result + isRequestingUserLikeIt.hashCode()
        result = 31 * result + (requestingUserSolving?.hashCode() ?: 0)
        return result
    }


}

class Grade {

    val font: String? = null
    val brazillian: String? = null
    val peak: String? = null
    val polish: String? = null
    val hueco: String? = null
    val redpointBasePoints: Int? = null
    val flashBasePoints: Int? = null
    val parentGradeId: String? = null
    val gymId: String? = null
    val eventId: String? = null
}


class Sector {
    val id: String? = null
    val name: String? = null
    val gymId: String? = null
    val isActive: Boolean? = null
}
