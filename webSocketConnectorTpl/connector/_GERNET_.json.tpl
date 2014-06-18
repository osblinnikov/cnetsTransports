<%import parsing_connector, json, generate_gernet
p = reload(parsing_connector)
gg = reload(generate_gernet)
p.parsingGernet(a)%>
"props": ${gg.getGernetProps(a)},
"depends":[{
  "path":"com.github.airutech.cnets.readerWriter",
  "ver": "[0.0.0,)"
},{
  "path":"com.github.airutech.cnets.runnablesContainer",
  "ver": "[0.0.0,)"
},{
  "path":"com.github.airutech.cnets.selector",
  "ver": "[0.0.0,)"
},{
  "path":"no.eyasys.mobileAlarm.types",
  "ver": "[0.0.0,)"
},{
  "path":"com.github.airutech.cnetsTransports.types",
  "ver": "[0.0.0,)"
},{
  "path":"com.github.airutech.cnets.types",
  "ver": "[0.0.0,)"
}],
"blocks": ${gg.getGernetBlocks(a)}