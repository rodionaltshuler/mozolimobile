package com.ottamotta.mozoli.dto

import kotlinx.serialization.Optional
import kotlinx.serialization.Serializable

@Serializable
class Problem {

    val id: String? = null
    @Optional
    val name: String = ""
    @Optional
    val photoId1: String? = null
    @Optional
    val photoId2: String? = null
    @Optional
    val photoId3: String? = null
    @Optional
    val comment: String? = null
    @Optional
    val redpointPoints: Int? = null
    @Optional
    val flashPoints: Int? = null
    @Optional
    val holdsColor: String? = null
    @Optional
    val stickerColor: String? = null
    @Optional
    val grade: Grade? = null
    @Optional
    val creatorId: String? = null
    @Optional
    val creatorFirstName: String? = null
    @Optional
    val creatorLastName: String? = null
    @Optional
    val creatorUserName: String? = null
    @Optional
    val creatorAva: String? = null
    @Optional
    val likesNumber: Int? = null
    @Optional
    val isRequestingUserLikeIt: Boolean? = false
    @Optional
    val eventId: String? = null
    @Optional
    val eventName: String? = null
    @Optional
    var requestingUserSolving: Solution? = null
    @Optional
    val gymId: String? = null
    @Optional
    val sector: Sector? = null
    @Optional
    val createdDate: String? = null

    fun solved() = requestingUserSolving?.solved()?:false

    fun solutionToSubmit(isFlash: Boolean = true, isRedpoint: Boolean = false, attempts: Int? = null): Solution {
        val solution = this.requestingUserSolving ?: Solution()
        return solution.apply {
            this.problemId = this@Problem.id
            this.isFlash = isFlash
            this.isRedpoint = isRedpoint
            this.attemptsNumber = attempts
        }
    }

    fun solutionToCancelSubmission(): Solution {
        val solution = this.requestingUserSolving ?: Solution()
        return solution.apply {
            this.problemId = this@Problem.id
            this.isFlash = false
            this.isRedpoint = false
            this.attemptsNumber = 0
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

@Serializable
class Grade {

    @Optional
    val font: String? = null
    @Optional
    val brazillian: String? = null
    @Optional
    val peak: String? = null
    @Optional
    val polish: String? = null
    @Optional
    val hueco: String? = null
    @Optional
    val redpointBasePoints: Int? = null
    @Optional
    val flashBasePoints: Int? = null
    @Optional
    val parentGradeId: String? = null
    @Optional
    val gymId: String? = null
    @Optional
    val eventId: String? = null
}


@Serializable
class Sector {
    val id: String? = null
    @Optional
    val name: String? = null
    @Optional
    val gymId: String? = null
    @Optional
    val isActive: Boolean? = null
}
