# GettyImageLoader 클래스

```kotlin
class GettyImagesLoader : AsyncTask<Void, Void, Pair<Array<Int>, ArrayList<Bitmap>>>
```

## 요약
[main_list](#main_list)를 비우고 gettyimages에서 이미지를 파싱하여 채웁니다.

AsyncTask의 마지막 타입인 Pair<Array<Int>, ArrayList<Bitmap>>는 `MainActivty`에서 이전, 다음 버튼 동작을
처리하기 위해 있습니다.

 아래의 json 형태를 표현했습니다. 
```json
{
  "id" : [0],
  "bitmaps": [bitmap, bitmap, bitmap ... ...]
}
```
id에 해당되는 Array<Int>는 리스트에서 해당 비트맵을 클릭시 해당 비트맵의 id값을 전달하기 위해서는 참조 변수가 필요하고
클래스를 새롭게 만드는 방법도 있지만 짧게 표현하기 위해 배열을 사용해 참조될 수 있게 표현했습니다.<br/>
이는 외부 `MainActiviy`에서 이전 버튼, 다음 버튼을 클릭시 최근 비트맵의 번호를 알아야 하기 때문에 id 상태 값을 만들었습니다.<br/>


## 멤버
### layout
```kotlin
class GettyImagesLoader : AsyncTask<Void, Void, Pair<Array<Int>, ArrayList<Bitmap>>>{
    private lateinit var layout : android.widget.LinearLayout
}
```
#### 요약
실제로 이미지를 넣을 동적으로 생성된 레이아웃입니다.

#### 상세
최대 3개의 ImageView 자식을 가집니다.
자식이 3개인 경우 `layout`을 새롭게 동적 할당합니다.
> layout은 항상 생성과 동시에 [main_list](#main_list)의 자식으로 연결됩니다.

### main_list
```kotlin
class GettyImagesLoader : AsyncTask<Void, Void, Pair<Array<Int>, ArrayList<Bitmap>>>{
    private var main_list : android.widget.LinearLayout
}
```
#### 요약
메인 리스트입니다.
> 이 프로젝트에서는 `MainActivity.main_list`를 참조합니다.

### main_img
#### 요약
메인 이미지입니다.
> 이 프로젝트에서는 `MainActivity.main_img`를 참조합니다.

### activity
#### 요약
타깃 액티비티입니다. 메인 쓰레드와 동적 view들을 생성하기 위해 있습니다.

### constructor
```kotlin
class GettyImagesLoader : AsyncTask<Void, Void, Pair<Array<Int>, ArrayList<Bitmap>>>{
    constructor(activity: Activity, main_list : LinearLayout, main_img : ImageView) : super(){
        //... ...
    }
}
```
#### 상세 
이미지 로딩과 동적으로 View를 만들기 위한 객체를 생성합니다.

#### 인수
| 이름 | 타입 | 내용 |
| -- | -- | -- |
| `activity` | `Activity` | 타깃이 되는 액티비티입니다. |
| `main_list` | `LinearLayout` | 타깃이 되는 리니어 레이아웃입니다 |
| `main_img` | `ImageView` | 동적으로 생성되는 이미지 뷰를 클릭시 같은 이미지로 채울 이미지 뷰입니다 |

### makeLayout
```kotlin
class GettyImagesLoader : AsyncTask<Void, Void, Pair<Array<Int>, ArrayList<Bitmap>>>{
    @MainThread
    private fun makeLayout(): LinearLayout{
        //... ...
    }
}
```
> 코딩 실수를 방지하기 위해 MainThread 제약 조건을 추가했습니다.
#### 상세
[layout](#layout)에 새롭게 동적 생성하는 메소드입니다.

### makeLayout
```kotlin
class GettyImagesLoader : AsyncTask<Void, Void, Pair<Array<Int>, ArrayList<Bitmap>>>{
    @MainThread
    private fun makeImageView(layout: LinearLayout, outID : Array<Int>, bitmaps : ArrayList<Bitmap>) : ImageView{
        //... ...
    }
}
```
> 코딩 실수를 방지하기 위해 MainThread 제약 조건을 추가했습니다.
#### 상세
이미지 뷰를 동적 생성하는 메소드입니다.

#### 인수
| 이름 | 타입 | 내용 |
| -- | -- | -- |
| `layout` | `LinearLayout` | 부모 레이아웃입니다. |
| `outID` | `Array<Int>` | `main_img`의 표시 ID 참조 값입니다. |
| `bitmaps` | `ArrayList<Bitmap>` | 대상 이미지입니다. 리스트의 마지막 요소로 선택합니다. |
> 동적으로 생성되는 이미지 뷰의 내부 식별 번호는 인수 `bitmaps.size - 1` 이 됩니다.
> 만약 클릭시 `outID`의 내용을 자신의 실별 번호로 만듭니다. 

### get_urls

#### 요약
이미지 url을 파싱하는 네이티브 인터페이스 함수입니다.