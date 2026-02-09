# -*- coding: utf-8; mode: Python; indent-tabs-mode: t; tab-width: 4; python-indent: 4 -*-

# Copyright (C) 2012  Olga Yakovleva <yakovleva.o.v@gmail.com>

# This program is free software: you can redistribute it and/or modify
# it under the terms of the GNU General Public License as published by
# the Free Software Foundation, either version 3 of the License, or
# (at your option) any later version.

# This program is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU General Public License for more details.

# You should have received a copy of the GNU General Public License
# along with this program.  If not, see <https://www.gnu.org/licenses/>.

import pyparsing as pyp

letters=set(u"абвгдеёжзийклмнопрстуфхцчшщьыъэюя")

class node(object):
	def __init__(self,src_str,loc,toks):
		self.location=loc
		self.initialize(toks)

	def initialize(self,toks):
		pass

class letter_node(node):
	def initialize(self,toks):
		self.letter=toks[0]

	def format_as_foma_regex(self):
		return self.letter

class sequence_node(node):
	def initialize(self,toks):
		self.subexpressions=toks[0].asList()

	def format_as_foma_regex(self):
		return "["+u" ".join(expr.format_as_foma_regex() for expr in self.subexpressions)+"]"

class alternation_node(node):
	def initialize(self,toks):
		self.subexpressions=toks[0].asList()

	def format_as_foma_regex(self):
		return "["+u" | ".join(expr.format_as_foma_regex() for expr in self.subexpressions)+"]"

class repetition_node(node):
	def initialize(self,toks):
		self.expression=toks[0][0]
		self.operator=toks[0][1]

	def format_as_foma_regex(self):
		if self.operator=="?":
			return "("+self.expression.format_as_foma_regex()+")"
		else:
			return self.expression.format_as_foma_regex()+self.operator

class letterset_node(node):
	def initialize(self,toks):
		self.negated=toks[0]=="^"
		self.letters=sorted(set(toks[-1].asList()))

	def format_as_foma_regex(self):
		return ("\\[" if self.negated else "[")+u"|".join(self.letters)+"]"

class dot_node(node):
	def format_as_foma_regex(self):
		return "?"

def make_factor(src_str,loc,toks):
	return repetition_node(src_str,loc,[toks[0]]) if len(toks[0])==2 else toks[0][0]

def make_alternation(src_str,loc,toks):
	return alternation_node(src_str,loc,[toks[0]]) if len(toks[0])>1 else toks[0][0]

Letter=pyp.one_of(list(letters))
Letterset=pyp.Literal("[").suppress()+pyp.Optional(pyp.Literal("^"))+pyp.Group(pyp.OneOrMore(Letter.copy()))+pyp.Literal("]").suppress()
Dot=pyp.Literal(".")
StartAnchor=pyp.Optional(pyp.StringStart()+pyp.Literal("^"))
EndAnchor=pyp.Optional(pyp.Literal("$")+pyp.StringEnd())
Letter.setParseAction(letter_node)
Letterset.setParseAction(letterset_node)
Dot.setParseAction(dot_node)
Atom=Letter|Letterset|Dot
Regex0=pyp.Forward()
Grouped=pyp.Literal("(").suppress()+Regex0+pyp.Literal(")").suppress()
Atom=Letter|Letterset|Dot|Grouped
Factor=pyp.Group(Atom+pyp.Optional(pyp.one_of("* + ?")))
Factor.setParseAction(make_factor)
Sequence=pyp.Group(pyp.OneOrMore(Factor))
Sequence.setParseAction(sequence_node)
Alternation=pyp.Group(Sequence+pyp.ZeroOrMore(pyp.Literal("|").suppress()+Sequence))
Alternation.setParseAction(make_alternation)
Regex0 <<= Alternation
Regex=StartAnchor.setResultsName("start_of_string")+Regex0.setResultsName("root")+EndAnchor.setResultsName("end_of_string")

def parse(string):
	result=Regex.parse_string(string)
	root=result["root"]
	while isinstance(root,pyp.ParseResults) and len(root)==1:
		root=root[0]
	result["root"]=root
	return result
