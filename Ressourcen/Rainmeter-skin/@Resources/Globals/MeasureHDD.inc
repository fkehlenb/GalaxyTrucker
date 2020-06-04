[Variables]
Drive1=C
Drive2=D
Drive3=E
Drive4=F

;---HDD--------------------------------------------------------------------

[MeasureHDD1_Total]
Measure=FreeDiskSpace
Drive=#Drive1#:
Total=1

[MeasureHDD1_Used]
Measure=FreeDiskSpace
Drive=#Drive1#:
InvertMeasure=1

[MeasureHDD1_Free]
Measure=FreeDiskSpace
Drive=#Drive1#:

[MeasureHDD1_Label]
Measure=FreeDiskSpace
Drive=#Drive1#:
Label=1

[MeasureHDD1_Type]
Measure=FreeDiskSpace
Drive=#Drive1#:

[MeasureHDD2_Total]
Measure=FreeDiskSpace
IgnoreRemovable=0
Drive=#Drive2#:
Total=1

[MeasureHDD2_Used]
Measure=FreeDiskSpace
IgnoreRemovable=0
Drive=#Drive2#:
InvertMeasure=1

[MeasureHDD2_Free]
Measure=FreeDiskSpace
IgnoreRemovable=0
Drive=#Drive2#:

[MeasureHDD2_Label]
Measure=FreeDiskSpace
IgnoreRemovable=0
Drive=#Drive2#:
Label=1

[MeasureHDD2_Type]
Measure=FreeDiskSpace
IgnoreRemovable=0
Drive=#Drive2#:

[MeasureHDD3_Total]
Measure=FreeDiskSpace
IgnoreRemovable=0
Drive=#Drive3#:
Total=1

[MeasureHDD3_Used]
Measure=FreeDiskSpace
IgnoreRemovable=0
Drive=#Drive3#:
InvertMeasure=1

[MeasureHDD3_Free]
Measure=FreeDiskSpace
IgnoreRemovable=0
Drive=#Drive3#:

[MeasureHDD3_Label]
Measure=FreeDiskSpace
IgnoreRemovable=0
Drive=#Drive3#:
Label=1

[MeasureHDD3_Type]
Measure=FreeDiskSpace
IgnoreRemovable=0
Drive=#Drive3#:

[MeasureHDD4_Total]
Measure=FreeDiskSpace
IgnoreRemovable=0
Drive=#Drive4#:
Total=1

[MeasureHDD4_Used]
Measure=FreeDiskSpace
IgnoreRemovable=0
Drive=#Drive4#:
InvertMeasure=1

[MeasureHDD4_Free]
Measure=FreeDiskSpace
IgnoreRemovable=0
Drive=#Drive4#:

[MeasureHDD4_Label]
Measure=FreeDiskSpace
IgnoreRemovable=0
Drive=#Drive4#:
Label=1

[MeasureHDD4_Type]
Measure=FreeDiskSpace
IgnoreRemovable=0
Drive=#Drive4#: