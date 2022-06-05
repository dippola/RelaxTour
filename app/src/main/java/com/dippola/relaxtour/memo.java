package com.dippola.relaxtour;

public class memo {
//    기존 페이지에 새로운트랙 업데이트 했을때 변경할거
//    1. AudioController > checkIsPlaying, checkIsPlaying, playingListindex0_1, playingListindex0_2, stopPage
//    2. AudioController > startTrack, checkPP, stopPage, startTrack
//    3. 각 page controller에 getSec, stopPage
//    4. 각 page에 set볼륨


//    새로운 페이지 업데이트 했을때 변경할거
//    1. databaseHandler > getPageName(), deleteAllPlayinglist
//    2. stopPlayingList
//    3. NotificationActionService > stopPage
//    4. SeekController > changeSeekInBottom, changeSeekInFavList
//    5. bottomSheetAdapter > changePageItemBackground
//    6. StorageManageAdapter > resetPage, changePageItemBackground, getPageName
}
