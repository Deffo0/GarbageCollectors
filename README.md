# GarbageCollectors
## Table of Contents
- [Overview](#Overview)
- [JAR Arguments](#JAR-Arguments)
- [Main Modules](#Main-Modules)
- [Test](#Test)
---
## Overview
+ Complelte implementation provided as JAR files resident in `GarbageCollectors/out/artifacts/` for 4 types of garbage collectors:
  - Mark & Sweep GC.
  - Mark & Compact GC.
  - Copy GC.
  - G1 GC.
+ What is the garbage collector?
>garbage collector manages the allocation and release of memory for an application. For developers working with managed code, this means that you don't have to write code to perform memory management tasks. Automatic memory management can eliminate common problems.
---
## JAR Arguments

1. Path of the heap csv file.
1. Path of the pointers csv file.
1. Path of the root objects txt file.
1. Path for the output file to be placed in.

Heap csv file Example:
| Object ID | Start Address| End Address |
| :---         |     :---:      |          ---: |
| 111111   | 100     | 249    |
| 222222     | 800       | 950      |
| 333333     | 1300       | 1490      |
| 444444     | 1750       | 1999      |

Pointers csv file Example:
| From-Object ID | To-Object ID|
| ---         |     ---     |
| 111111   | 222222     |
| 222222     | 333333     |
| 444444     | 111111       |

Root txt file Example:
```
111111
222222
```

---
## Main Modules
| Module | Description |
| --- | --- |
| `ObjectInfo` | Includes all attributes about the address of the object on the heap and its pointers to the other objects in the heap hierarchy |
| `HeapConstructor` | Constructs the heap by traversing the csv files and makes appropriate links between objects |
---
## Test
+ it was generated on number of corners cases for the allocation graph of the heap and its objects.
