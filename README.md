## ✅ Commit Convention
**Message Structure**
```
type. Subject

body

footer
```
**The Type**
|    Type    | Description                 |
|:----------:|-----------------------------|
|   `feat`   | 신규 기능 구현 작업                 |
|   `fix`    | 버그 수정                       |
| `refactor` | 리팩토링 작업                     |
|  `style`   | 코드 스타일 관련 작업 (코드 의미 변경 X) |
|  `rename`  | 변수/클래스/메서드 명 변경             |
|  `build`   | dependencies 업데이트 |
|  `chore`   | configs 변화 등 그 외 작업 (코드 변경 X) |
|   `docs`   | 문서 관련 작업                   |

**예시**
```
Feat. 소셜 로그인 기능 구현

카카오 소셜 로그인 구현

Resolves: #21 or Fixed: #21
See also: #20, #26
```

## 🧱 Branch Strategy
- Git flow
  - main
    - 배포 target
  - dev

## 👨‍💻 Code review
- PR 시 모두의 approve 를 받은 경우에 dev 에 merge
