# Grus

一个基于 BukkitAPI 的修仙插件

[![Java CI with Gradle](https://github.com/602723113/Grus/actions/workflows/gradle.yml/badge.svg)](https://github.com/602723113/Grus/actions/workflows/gradle.yml)

> 修仙觅长生，热血任逍遥，踏莲曳波涤剑骨，凭虚御风塑圣魂！ —— 《凡人修仙传》

### 境界篇

在 Grus 的 [默认配置](https://github.com/602723113/Grus/blob/main/src/main/resources/config.yml)
当中修仙者境界划分为`练气、筑基初阶、筑基中阶、筑基巅峰、结丹、元婴、化神、炼虚、合体、大乘`十重境界。

|境界|所需修为|
|:---:|:---:|
|炼气|500|
|筑基初阶|1500|
|筑基中阶|7500|
|筑基巅峰|15000|
|结丹|50000|
|元婴|500000|
|化神|5000000|
|炼虚|50000000|
|合体|500000000|
|大乘|5000000000|

每一种境界所需的 [^修为值] 都可以自定义, 并且在 config.yml 当中可以自行删改修仙者的境界配置, 可以做到不同服务器有不同的修仙模式

[^修为值]: 即玩家的修仙时所积攒的经验值

详情请见 config.yml 有更详细的介绍

---

### 灵根篇

灵根指修仙者的属性, 表示修仙者的修行素质, 特殊的功法可以设定只有特殊的灵根才能学习

在 Grus 的 [默认配置](https://github.com/602723113/Grus/blob/main/src/main/resources/config.yml)
当中共设定有默认五大灵根分别是: `金、木、水、火、土`

玩家获得灵根后则会有以下几种效果

- **天灵根**：只有一种属性的单一灵根，灵根充裕。修炼速度是普通灵根的数倍。
- **真灵根**：具有两、三种属性的灵根，每种属性灵根充裕。修炼速度较快。
- **伪灵根**：具有四、五种属性的灵根，很杂且不充裕，每种属性的灵根都不完全，修炼速度很慢。

修炼速度设定可见 config.yml 内进行设定

---

### 法器篇

另见 [Grus-MagicTreasures](https://github.com/GrusWorld/Grus-MagicTreasures)

---

### 符箓篇

等人写

--- 

### 功法篇

等人写