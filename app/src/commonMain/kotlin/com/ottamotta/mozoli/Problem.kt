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
    val id : String? = null
    val name: String? = null
    val gymId: String? = null
    val isActive: Boolean? = null
}
